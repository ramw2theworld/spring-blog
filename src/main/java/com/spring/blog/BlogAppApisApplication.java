package com.spring.blog;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

@SpringBootApplication
public class BlogAppApisApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		Converter<Date, String> toStringDate = new AbstractConverter<Date, String>() {
			protected String convert(Date source) {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
				return source != null ? formatter.format(source) : null;
			}
		};
		Converter<String, Date> toDate = new AbstractConverter<String, Date>() {
			protected Date convert(String source) {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
				try {
					return source != null ? formatter.parse(source) : null;
				} catch (ParseException e) {
					System.err.println("Date parsing error: " + e.getMessage());
					return null;
				}
			}
		};

		modelMapper.addConverter(toDate);
		modelMapper.addConverter(toStringDate);

		return modelMapper;
	}
}
