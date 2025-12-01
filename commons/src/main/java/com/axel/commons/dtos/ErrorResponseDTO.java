package com.axel.commons.dtos;


public record ErrorResponseDTO(
        int codigoEstado,
        String mensaje
) {
}
