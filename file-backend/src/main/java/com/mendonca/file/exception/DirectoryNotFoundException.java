package com.mendonca.file.exception;

public class DirectoryNotFoundException extends RuntimeException {
   
	private static final long serialVersionUID = 1L;

	public DirectoryNotFoundException(String message) {
        super(message);
    }
}

