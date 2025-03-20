package es.codeurjc.backend.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public record NewConcertDTO(

String concertName,
String concertDetails,
String concertDate,
String concertTime,
String location,
Integer stadiumPrice,
Integer trackPrice,
String map,
MultipartFile concertImage,
List<Long> artistIds) {

} 
