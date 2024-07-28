package com.calculadoralaser.models.dao;


//import java.util.List;


import org.springframework.data.repository.CrudRepository;

import com.calculadoralaser.models.entity.TransaccionesGastos;



public interface ITransaccionesGastosDao extends CrudRepository<TransaccionesGastos,Long>{
	
//	@Query(
//			value="select tg.id,tg.cantidad,tg.create_at,tg.descripcion,\r\n"
//					+ "tg.flag_vigencia,tg.last_update,tg.nota,cg.nombre as concepto_gasto_id,cu.nombre as cuenta_id \r\n"
//					+ "from transacciones_gastos tg\r\n"
//					+ "inner join conceptos_gastos cg on tg.concepto_gasto_id=cg.id\r\n"
//					+ "inner join cuentas cu on tg.cuenta_id=cu.id\r\n"
//					+ ";",
//			nativeQuery=true)
//	public List<TransaccionesGastos> encontrarTodos();
	
//	@Query("select TG from TransaccionesGastos TG join fetch TG.conceptosGastos CG join fetch TG.cuentas C")
//	public List<TransaccionesGastos> encontrarTodos();
}
