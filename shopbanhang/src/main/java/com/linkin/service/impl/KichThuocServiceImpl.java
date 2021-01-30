package com.linkin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linkin.dao.KichThuocDao;
import com.linkin.entity.KichThuoc;
import com.linkin.model.KichThuocDTO;
import com.linkin.service.KichThuocService;

@Transactional
@Service
public class KichThuocServiceImpl implements KichThuocService {
	@Autowired
	KichThuocDao kichThuocDao;

	@Override
	public void insert(KichThuocDTO kichThuocDTO) {
		KichThuoc kichThuoc = new KichThuoc();
		kichThuoc.setName(kichThuocDTO.getName());
		kichThuocDao.insert(kichThuoc);
		kichThuocDTO.setId(kichThuoc.getId());
	}

	@Override
	public void update(KichThuocDTO kichThuocDTO) {
		KichThuoc kichThuoc = kichThuocDao.get(kichThuocDTO.getId());
		if (kichThuoc != null) {
			kichThuoc.setName(kichThuocDTO.getName());
			kichThuoc.setId(kichThuocDTO.getId());
			kichThuocDao.update(kichThuoc);
		}
	}

	@Override
	public void delete(Long id) {
		KichThuoc kichThuoc = kichThuocDao.get(id);
		if (kichThuoc != null) {
			kichThuocDao.delete(kichThuoc);
		}

	}

	@Override
	public KichThuocDTO get(Long id) {
		KichThuoc kichThuoc = kichThuocDao.get(id);
		KichThuocDTO kichThuocDTO = new KichThuocDTO();
		kichThuocDTO.setId(kichThuoc.getId());
		kichThuocDTO.setName(kichThuoc.getName());
		return kichThuocDTO;
	}

	@Override
	public List<KichThuocDTO> search(String name, int start, int length) {
		List<KichThuoc> categories = kichThuocDao.search(name, start, length);
		List<KichThuocDTO> kichThuocDTOs = new ArrayList<KichThuocDTO>();
		for (KichThuoc kichThuoc : categories) {
			KichThuocDTO kichThuocDTO = new KichThuocDTO();
			kichThuocDTO.setId(kichThuoc.getId());
			kichThuocDTO.setName(kichThuoc.getName());
			kichThuocDTOs.add(kichThuocDTO);
		}
		return kichThuocDTOs;
	}


}
