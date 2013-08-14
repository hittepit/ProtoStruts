package be.fabrice.proto.service;

import java.util.List;

import be.fabrice.proto.service.vo.BookVo;

public interface BookVoService {
	List<BookVo> findAll();
}
