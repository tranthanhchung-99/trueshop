package com.linkin.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkin.dao.ThuongHieuDao;
import com.linkin.entity.ThuongHieu;
import com.linkin.model.ThuongHieuDTO;
import com.linkin.service.ThuongHieuService;

@Transactional
@Service
public class ThuongHieuServiceImpl implements ThuongHieuService{
	
	@Autowired
	ThuongHieuDao thuongHieuDao;

	@Override
	public void insert(ThuongHieuDTO thuongHieuDTO) {
		ThuongHieu thuongHieu = new ThuongHieu();
		thuongHieu.setName(thuongHieuDTO.getName());
		thuongHieuDao.insert(thuongHieu);
		thuongHieuDTO.setId(thuongHieu.getId());
	}

	@Override
	public void update(ThuongHieuDTO thuongHieuDTO) {
		ThuongHieu thuongHieu = thuongHieuDao.get(thuongHieuDTO.getId());
		if (thuongHieu != null) {
			thuongHieu.setName(thuongHieuDTO.getName());

			thuongHieuDao.update(thuongHieu);
		}
	}

	@Override
	public void delete(long id) {
		ThuongHieu thuongHieu = thuongHieuDao.get(id);
		if (thuongHieu != null) {
			thuongHieuDao.delete(thuongHieu);
		}
	}

	@Override
	public ThuongHieuDTO get(long id) {
		ThuongHieu thuongHieu = thuongHieuDao.get(id);
		if (thuongHieu !=null) {
			ThuongHieuDTO thuongHieuDTO = new ThuongHieuDTO();
			thuongHieuDTO.setId(thuongHieu.getId());
			thuongHieuDTO.setName(thuongHieu.getName());
			return thuongHieuDTO;
		}
		return null;
	}

	@Override
	public List<ThuongHieuDTO> search(String name, int start, int length) {
		List<ThuongHieu> thuongHieus = thuongHieuDao.search(name, start, length);
		List<ThuongHieuDTO> thuongHieuDTOs = new ArrayList<ThuongHieuDTO>();
		for (ThuongHieu thuongHieu : thuongHieus) {
			ThuongHieuDTO thHieuDTO = new ThuongHieuDTO();
			thHieuDTO.setId(thuongHieu.getId());
			thHieuDTO.setName(thuongHieu.getName());
			thuongHieuDTOs.add(thHieuDTO);
		}
		return thuongHieuDTOs;
	}

}
