package com.api_pagamento.elastic_search.service;

import com.api_pagamento.elastic_search.domain.dto.TransacaoDTO;
import com.api_pagamento.elastic_search.domain.exception.InsercaoNaoPermitidaException;
import com.api_pagamento.elastic_search.domain.exception.TransacaoInexistenteException;
import com.api_pagamento.elastic_search.domain.model.Transacao;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//@Transactional

//https://www.devmedia.com.br/conheca-o-spring-transactional-annotations/32472
//"A boa prática é sempre colocar o @Transactional nos métodos que precisam de transação, por exemplo: salvar, alterar,
//excluir, etc., pois assim você garante que eles vão ser executados dentro um contexto transacional e o rollback
//será feito caso ocorra algum erro

@Transactional
public interface TransacaoService {

    List<TransacaoDTO> findBy(String value) throws TransacaoInexistenteException;

    TransacaoDTO findById(String id) throws TransacaoInexistenteException;
    List<TransacaoDTO> findAll() throws TransacaoInexistenteException;
    TransacaoDTO pagar(Transacao transacao) throws InsercaoNaoPermitidaException;
    TransacaoDTO estornar(String id) throws TransacaoInexistenteException;

}
