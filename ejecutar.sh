#!/bin/bash

#Actualizar el sistema

sudo apt update && sudo apt upgrade -y

#Instalar Postgresql

sudo apt install postgresql postgresql-contrib -y

sudo systemctl start postgresql.service

user_var="lb80user"
pass_var="voyadormir"

sudo su - postgres -c "psql -c \"create user $user_var with password '$pass_var';\""

sudo su - postgres -c "psql -c \"alter user lb80user with SUPERUSER;\""
sudo su - postgres -c "psql -c \"create database lb80;\""
sudo -u postgres psql -d lb80 -c "CREATE SCHEMA esquema1;"
sudo su - postgres -c "psql -c \"GRANT ALL PRIVILEGES ON DATABASE lb80 to $user_var;\""
sudo chgrp postgres /home/lb80user/
#Restaurar base de datos
sudo -u postgres psql -d lb80 -c "\i restaurar_base_datos_ubuntu_24.sql"

#Permitir conexiones remotas
sudo ufw allow 5432/tcp

# Editar archivo postgresql.conf
sudo sed -i "s/#listen_addresses = 'localhost'/listen_addresses = '*'/" /etc/postgresql/16/main/postgresql.conf
echo 'postgresql.conf editado'
# Editar archivo pg_hba.conf
sudo sed -i '$a host	all		all		0.0.0.0/0		md5' /etc/postgresql/16/main/pg_hba.conf
echo 'pg_hba.conf editado'
# Reiniciar postgresql
sudo systemctl restart postgresql

#Instalar java
sudo apt install openjdk-11-jdk -y

#Instalar maven
sudo apt install maven -y

#instalar git
sudo apt install git-all

sudo mkdir app
cd app

#clonar app
sudo git clone https://github.com/slaydep/CalculadoraLaser.git

cd CalculadoraLaser/

#Creacion del archivo .jar
sudo mvn clean install

cd ~
#Creacion servicio
# Nombre del servicio
servicio="laserbogota80-app"

# Ruta al archivo de servicio
ruta_servicio="/etc/systemd/system/${servicio}.service"

# Contenido del archivo de servicio
contenido=$(cat <<EOF
[Unit]
Description=$servicio
After=network.target

[Service]
Type=simple
User=$user_var
ExecStart=/home/lb80user/app/CalculadoraLaser/target laserbogota80-app.jar
Restart=always

[Install]
WantedBy=multi-user.target
EOF
)

# Crear el archivo de servicio
sudo echo "$contenido" > "$ruta_servicio"

# Recargar la configuración de Systemd
sudo systemctl daemon-reload

# Iniciar el servicio
sudo systemctl start $servicio

# Habilitar el servicio para que se inicie al arrancar el sistema
sudo systemctl enable $servicio

#Instalar NGinx
sudo apt install nginx -y

conf_nginx=$(cat <<EOF
server {
    listen 80 default_server;
	listen [::]:80 default_server;

    server_name catalogo.laserbogota80.com;

    location / {
        proxy_pass        http://localhost:5000;
		proxy_set_header  X-Real-IP $remote_addr;
		proxy_set_header  X-Forwarded-For $proxy_add_x_forwarded_for;
		proxy_set_header  Host $http_host;   

    }
}
EOF
)

ruta_nginx="/etc/nginx/sites-available/laserbogota80-app"

sudo echo "$conf_nginx" > "$ruta_nginx"

sudo unlink /etc/nginx/sites-enabled/default

sudo ln -s $ruta_nginx /etc/nginx/sites-enabled/

# Probar la configuración de Nginx
sudo nginx -t

# Reiniciar Nginx
sudo systemctl restart nginx