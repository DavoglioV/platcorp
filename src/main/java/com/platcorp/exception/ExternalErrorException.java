package com.platcorp.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.platcorp.domain.Erro;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ExternalErrorException extends Exception {

	private static final long serialVersionUID = -573360898275622847L;

	@SuppressWarnings("unused")
	private List<Erro> lstError;
	
	public ExternalErrorException(Class<?> classe, String message) {
        super(classe.getSimpleName() + ": " + message);
    }

    public ExternalErrorException(List<Erro> list) {
        this.lstError = list;
    }
}
