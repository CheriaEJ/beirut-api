package com.ega.services;

import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ega.dao.MahasiswaDao;
import com.ega.entities.Mahasiswa;
import com.ega.services.SimpleCRUD;

@Service(value = "simpleCRUD")
@Transactional(readOnly = true)
public class SimpleCRUDService implements SimpleCRUD {

  @Autowired
  private MahasiswaDao mahasiswaDao;

  @Override
  public Mahasiswa findByNama(String nama) {
    return getMahasiswaDao().findByNama(nama);
  }

  @Override
  public Mahasiswa findMahasiswaById(int id) {
    return getMahasiswaDao().findOne(id);
  }

  @Override
  //  @Transactional(readOnly = false)
  public Mahasiswa findMahasiswaDetail(int id) {
    System.out.println("ambil mahasiswa");
    Mahasiswa mahasiswa = mahasiswaDao.findOne(id);
    System.out.println("ambil relasi mahasiswa");
    Hibernate.initialize(mahasiswa.getMataKuliah());
    return mahasiswa;
  }

  public MahasiswaDao getMahasiswaDao() {
    return mahasiswaDao;
  }

  @Override
  @Transactional(readOnly = false)
  public void saveMahasiswa(Mahasiswa mahasiswa) {
    getMahasiswaDao().save(mahasiswa);
  }

  public void setMahasiswaDao(MahasiswaDao mahasiswaDao) {
    this.mahasiswaDao = mahasiswaDao;
  }

  @Override
  public List<Mahasiswa> getAll() {
	return getMahasiswaDao().findAll();
  }

@Override
public void deleteMahasiswa(Mahasiswa mahasiswa) {
	getMahasiswaDao().delete(mahasiswa);
  }
}