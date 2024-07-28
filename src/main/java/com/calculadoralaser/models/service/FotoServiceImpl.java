package com.calculadoralaser.models.service;

import com.calculadoralaser.models.dao.IFotoDao;
import com.calculadoralaser.models.entity.Foto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FotoServiceImpl implements IFotoService{

    @Autowired
    private IFotoDao fotoDao;

    @Override
    public List<Foto> findAll() {
        return (List<Foto>) fotoDao.findAll();
    }
    

    @Override
    public void save(Foto foto) {
        fotoDao.save(foto);
    }

    @Override
    public void delete(Long id) {
        fotoDao.deleteById(id);
    }

    @Override
    public Optional<Foto> findById(Long id) {
        return fotoDao.findById(id);
    }


	@Override
	public Page<Foto> findAll(Pageable pageable) {
		
		return fotoDao.findAll(pageable);
	}


	@Override
	public List<Foto> findByNombres(String term) {
		// TODO Auto-generated method stub
		return fotoDao.findByNombres(term);
	}
	
	@Override
	public Optional<Foto> findByNombre(String term) {
		// TODO Auto-generated method stub
		return fotoDao.findByNombre(term);
	}


	@Override
	public Page<Foto> findAllByNombre(String busqueda, Pageable pageRequest) {
		// TODO Auto-generated method stub
		return fotoDao.findAllByNombre(busqueda, pageRequest);
	}


}
