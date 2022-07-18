package com.api_pagamento.elastic_search.service;

import com.api_pagamento.elastic_search.repository.TransacaoRepository;
import com.api_pagamento.elastic_search.domain.dto.TransacaoDTO;
import com.api_pagamento.elastic_search.domain.dto.util.Mapper;
import com.api_pagamento.elastic_search.domain.enumeration.StatusEnum;
import com.api_pagamento.elastic_search.domain.exception.InsercaoNaoPermitidaException;
import com.api_pagamento.elastic_search.domain.exception.TransacaoInexistenteException;
import com.api_pagamento.elastic_search.domain.model.Transacao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

//@Service

//A anotação @Service é usada em sua camada de serviço e anota classes que realizam tarefas de serviço, muitas vezes
//você não a usa, mas em muitos casos você usa essa anotação para representar uma prática recomendada. Por exemplo,
//você poderia chamar diretamente uma classe DAO para persistir um objeto em seu banco de dados, mas isso é horrível.
//É muito bom chamar uma classe de serviço que chama um DAO. Isso é uma boa coisa para executar o padrão de separação
//de interesses.

@Service

//@Transactional
//https://www.devmedia.com.br/conheca-o-spring-transactional-annotations/32472
//"A boa prática é sempre colocar o @Transactional nos métodos que precisam de transação, por exemplo: salvar, alterar,
//excluir, etc., pois assim você garante que eles vão ser executados dentro um contexto transacional e o rollback
//será feito caso ocorra algum erro."

@Transactional

//@RequiredArgsConstructor
//Gera um construtor com argumentos necessários. Os argumentos obrigatórios são campos finais e campos com restrições como @NonNull.

@RequiredArgsConstructor
public class TransacaoServiceImp implements TransacaoService {

    private final TransacaoRepository transacaoRepository;

    @Override
    public List<TransacaoDTO> findBy(String value) throws TransacaoInexistenteException {

        Iterable<Transacao> transacoes = transacaoRepository.findBy(value);
        List<TransacaoDTO> transacoesDTO = new ArrayList<>();
        transacoes.forEach(t-> transacoesDTO.add((TransacaoDTO) Mapper.convert(t, TransacaoDTO.class)));

        if(transacoesDTO.size() != 0){
            return transacoesDTO;
        }else{
            throw new TransacaoInexistenteException();
        }
    }

    @Override
    public TransacaoDTO findById(String id) throws TransacaoInexistenteException {
        TransacaoDTO transacaoDTO = (TransacaoDTO) transacaoRepository.findById(id).map(t -> Mapper.convert(t, TransacaoDTO.class)).orElse(null);
        if(transacaoDTO != null){
            return transacaoDTO;
        }else{
            throw new TransacaoInexistenteException();
        }
    }

    @Override
    public List<TransacaoDTO> findAll() throws TransacaoInexistenteException {
        Iterable<Transacao> transacoes = transacaoRepository.findAll();
        List<TransacaoDTO> transacoesDTO = new ArrayList<>();
        transacoes.forEach(t-> transacoesDTO.add((TransacaoDTO) Mapper.convert(t, TransacaoDTO.class)));

        if(transacoesDTO.size() != 0){
            return transacoesDTO;
        }else{
            throw new TransacaoInexistenteException();
        }
    }

    @Override
    public TransacaoDTO pagar(Transacao transacao) throws InsercaoNaoPermitidaException {

        if(transacao.getDescricao().getStatus() == null && transacao.getDescricao().getNsu() == null && transacao.getDescricao().getCodigoAutorizacao() == null && transacao.getId() == null) {
            transacao.getDescricao().setNsu("1234567890");
            transacao.getDescricao().setCodigoAutorizacao("147258369");
            transacao.getDescricao().setStatus(StatusEnum.AUTORIZADO);
            return (TransacaoDTO) Mapper.convert(transacaoRepository.save(transacao), TransacaoDTO.class);
        }else{
            throw new InsercaoNaoPermitidaException();
        }

    }

    public TransacaoDTO estornar(String id) throws TransacaoInexistenteException {

        try{

            Transacao transacao = (Transacao) Mapper.convert(findById(id), Transacao.class);
            transacao.getDescricao().setStatus(StatusEnum.NEGADO);

            transacaoRepository.save(transacao);

            return (TransacaoDTO) Mapper.convert(transacao, TransacaoDTO.class);
        }catch (TransacaoInexistenteException ex){
            throw new TransacaoInexistenteException();
        }

    }

}
