package com.calculadoralaser.models.service;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IUploadFileService {
	
	public Resource load(String filename) throws MalformedURLException;
	
	public String copy(MultipartFile file) throws IOException;
	public String copyCliente(MultipartFile file) throws IOException;
	public String copyVector(MultipartFile file) throws IOException;
	public String copyProducto(MultipartFile file) throws IOException;
	public boolean delete(String filename);
	public boolean deleteCliente(String filename);
	public boolean deleteVector(String filename);
	public boolean deleteProducto(String filename);
	
	public void deleteAll();
	public void init() throws IOException;

	public String copyFoto(MultipartFile fot) throws IOException;
	boolean deleteFoto(String filename);

	Resource loadFoto(String filename) throws MalformedURLException;

	Resource loadVector(String filename) throws MalformedURLException;

}
