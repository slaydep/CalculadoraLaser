package com.calculadoralaser.models.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileServiceImpl implements IUploadFileService {

	private final Logger log = LoggerFactory.getLogger(getClass());
	
//  en pc consultor
	private final static String UPLOADS_FOLDER = "C:/tmp/lb80/uploads";
	private final static String UPLOADS_FOLDER_CLIENTES = UPLOADS_FOLDER+"/clientes";
	private final static String UPLOADS_FOLDER_VECTORES = UPLOADS_FOLDER+"/vectores";
	private final static String UPLOADS_FOLDER_PRODUCTOS = UPLOADS_FOLDER+"/productos";
	private final static String UPLOADS_FOLDER_FOTOS = UPLOADS_FOLDER+"/fotos";
	
//  en pc almacen lb80
//	private final static String UPLOADS_FOLDER = "C:/daniel/uploads";
//	private final static String UPLOADS_FOLDER_CLIENTES = "C:/daniel/uploads/clientes";
//	private final static String UPLOADS_FOLDER_VECTORES = "C:/daniel/uploads/vectores";
//	private final static String UPLOADS_FOLDER_PRODUCTOS = "C:/daniel/uploads/productos";
//	private final static String UPLOADS_FOLDER_FOTOS = "C:/daniel/uploads/fotos";	

//  en ubuntu
//	private final static String UPLOADS_FOLDER = "/home/lb80user/uploads";
//	private final static String UPLOADS_FOLDER_CLIENTES = "/home/lb80user/uploads/clientes";
//	private final static String UPLOADS_FOLDER_VECTORES = "/home/lb80user/uploads/vectores";
//	private final static String UPLOADS_FOLDER_PRODUCTOS = "/home/lb80user/uploads/productos";
//	private final static String UPLOADS_FOLDER_FOTOS = "/home/lb80user/uploads/fotos";

	
	@Override
	public String copyCliente(MultipartFile file) throws IOException {
		String uniqueFilename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		Path rootPath = getPathClientes(uniqueFilename);

		log.info("rootPath: " + rootPath);

		Files.copy(file.getInputStream(), rootPath);

		return uniqueFilename;
	}
	public String copyProducto(MultipartFile file) throws IOException {
		String uniqueFilename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		Path rootPath = getPathProductos(uniqueFilename);

		log.info("rootPath: " + rootPath);

		Files.copy(file.getInputStream(), rootPath);

		return uniqueFilename;
	}
	
	@Override
	public String copyVector(MultipartFile file) throws IOException {
		String uniqueFilename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		Path rootPath = getPathVectores(uniqueFilename);

		log.info("rootPath: " + rootPath);

		Files.copy(file.getInputStream(), rootPath);

		return uniqueFilename;
	}
	
	@Override
	public String copyFoto(MultipartFile file) throws IOException {
		String uniqueFilename =file.getOriginalFilename();
		Path rootPath = getPathFotos(uniqueFilename);

		log.info("rootPath: " + rootPath);

		Files.copy(file.getInputStream(), rootPath);

		return uniqueFilename;
	}

	

	public Path getPath(String filename) {
		return Paths.get(UPLOADS_FOLDER).resolve(filename).toAbsolutePath();
	}
	public Path getPathClientes(String filename) {
		return Paths.get(UPLOADS_FOLDER_CLIENTES).resolve(filename).toAbsolutePath();
	}
	public Path getPathVectores(String filename) {
		return Paths.get(UPLOADS_FOLDER_VECTORES).resolve(filename).toAbsolutePath();
	}
	public Path getPathProductos(String filename) {
		return Paths.get(UPLOADS_FOLDER_PRODUCTOS).resolve(filename).toAbsolutePath();
	}
	public Path getPathFotos(String filename) {
		return Paths.get(UPLOADS_FOLDER_FOTOS).resolve(filename).toAbsolutePath();
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(Paths.get(UPLOADS_FOLDER).toFile());
		
	}

	@Override
	public void init() throws IOException {
		Files.createDirectory(Paths.get(UPLOADS_FOLDER));
		
	}

	

	@Override
	public Resource load(String filename) throws MalformedURLException {
		Path pathFoto = getPath(filename);
		log.info("pathFoto: " + pathFoto);
		Resource recurso = null;
		recurso = new UrlResource(pathFoto.toUri());
		if (!recurso.exists() || !recurso.isReadable()) {
			throw new RuntimeException("Error: no se puede cargar la imagen" + pathFoto.toString());
		}

		return recurso;
	}
	@Override
	public Resource loadFoto(String filename) throws MalformedURLException {
		Path pathFoto = getPathFotos(filename);
		log.info("pathFoto: " + pathFoto);
		Resource recurso = new UrlResource(pathFoto.toUri());
		if (!recurso.exists() || !recurso.isReadable()) {
			throw new RuntimeException("Error: no se puede cargar la foto" + pathFoto.toString());
		}

		return recurso;
	}
	
	@Override
	public Resource loadVector(String filename) throws MalformedURLException {
		Path pathVector = getPathVectores(filename);
		log.info("pathVector: " + pathVector);
		Resource recurso = new UrlResource(pathVector.toUri());
		
		if (!recurso.exists() || !recurso.isReadable()) {
			throw new RuntimeException("Error: no se puede cargar el vector" + pathVector.toString());
		}

		return recurso;
	}
	
	@Override
	public boolean deleteCliente(String filename) {
		Path rootPath = getPathClientes(filename);
		File archivo = rootPath.toFile();

		if (archivo.exists() && archivo.canRead()) {
			if (archivo.delete()) {
				return true;
			} 
		}
		return false;
	}

	@Override
	public boolean deleteVector(String filename) {
		Path rootPath = getPathVectores(filename);
		File archivo = rootPath.toFile();

		if (archivo.exists() && archivo.canRead()) {
			if (archivo.delete()) {
				return true;
			} 
		}
		return false;
	}
	@Override
	public boolean deleteProducto(String filename) {
		Path rootPath = getPathProductos(filename);
		File archivo = rootPath.toFile();

		if (archivo.exists() && archivo.canRead()) {
			if (archivo.delete()) {
				return true;
			} 
		}
		return false;
	}
	
	
	@Override
	public boolean deleteFoto(String filename) {
		Path rootPath = getPathFotos(filename);
		File archivo = rootPath.toFile();

		if (archivo.exists() && archivo.canRead()) {
			if (archivo.delete()) {
				return true;
			} 
		}
		return false;
	}
	@Override
	public String copy(MultipartFile file) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean delete(String filename) {
		Path rootPath = getPath(filename);
		File archivo = rootPath.toFile();

		if (archivo.exists() && archivo.canRead()) {
			if (archivo.delete()) {
				return true;
			} 
		}
		return false;
	}

	
}
