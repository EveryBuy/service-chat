package ua.everybuy;

import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import ua.everybuy.buisnesslogic.service.file.FileUrlService;
import ua.everybuy.buisnesslogic.service.file.S3CleanService;

import java.util.List;

@SpringBootApplication
@EnableScheduling
@RequiredArgsConstructor
public class EverybuyChatServiceApplication {
	private final S3CleanService s3CleanService;
	private final FileUrlService fileUrlService;

	public static void main(String[] args) {
		SpringApplication.run(EverybuyChatServiceApplication.class, args);
	}
//	@Bean
//	CommandLineRunner commandLineRunner(ApplicationContext ctx) {
//		return args -> {
//			s3CleanService.cleanAmazonBucket();
//
//			int count = 0;
//			List<String> urlsInDb = fileUrlService.findAllFilesUrls();
//			for (String s : s3CleanService.findAllFilesUrls()){
//					System.out.println(s);
//					count++;
//			}
//			System.out.println(count);
////			System.out.println();
////			System.out.println();
////			System.out.println();
////			for (String s : fileUrlService.findAllFilesUrls()){
////				System.out.println(s);
////			}
////			System.out.println(fileUrlService.findAllFilesUrls().size());
//		};
//	}
}
