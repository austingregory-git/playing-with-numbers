package com.pwn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.*;

@SpringBootApplication
public class PlayingWithNumbersApplication {

	public static void main(String[] args) {
		ApplicationContext appContext = SpringApplication.run(PlayingWithNumbersApplication.class, args);
		PlayingWithAlphanumericNumbers pwanService = appContext.getBean(PlayingWithAlphanumericNumbers.class);
		pwanService.start();
		//PlayingWithNumbersService pwnService = appContext.getBean(PlayingWithNumbersService.class);
		//pwnService.startDefault();
		//for section c, just do pwnService.startDefault instead of the rest of this stuff
		/*pwnService.startUserStories();
		Scanner scan = new Scanner(System.in);
		while(true) {
			System.out.println("Would you like to run the program again? (y/n)");
			String input = scan.nextLine();
			if(input.equals("n")) {
				System.out.println("Exiting");
				break;
			}
			else if(!input.equals("y")) {
				System.out.println("Invalid response. Enter y for yes, or n for no.");
			}
			else {
				pwnService.startUserStories();
			}
		}
		scan.close();*/
		System.exit(0);
	}
}
