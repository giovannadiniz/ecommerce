package com.gec.ecommerce.bases;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public interface BaseMapper<Entidade, DataTransferObject, Requisicao, Resposta> {

    DataTransferObject converterParaDataTransferObject(Entidade entidade);

    Entidade converterParaEntidade(DataTransferObject dataTransferObject);

    Resposta converterEntidadeParaResposta(Entidade entidade);

    Entidade converterRequisicaoParaEntidade(Requisicao requisicao);

    List<DataTransferObject> converterListaEntidadesParaListaDataTransferObjects(List<Entidade> entidades);

    List<Entidade> converterListaDataTransferObjectsParaListaEntidades(List<DataTransferObject> dataTransferObjects);

    List<Resposta> converterListaEntidadesParaListaRespostas(List<Entidade> entidades);

    void atualizarEntidadeComDataTransferObject(DataTransferObject dataTransferObject, @MappingTarget Entidade entidade);

    void atualizarEntidadeComRequisicao(Requisicao requisicao, @MappingTarget Entidade entidade);
}