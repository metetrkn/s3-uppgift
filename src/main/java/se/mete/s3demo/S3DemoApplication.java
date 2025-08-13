package se.mete.s3demo;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import software.amazon.awssdk.services.s3.S3Client;

import java.util.Scanner;

@SpringBootApplication
public class S3DemoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(S3DemoApplication.class, args
        );
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        Dotenv dotenv = Dotenv.load();

        // Koppla upp bucketen
        String bucketName = dotenv.get("BUCKET_NAME");
        String accessKey = dotenv.get("ACCESS_KEY");
        String secretKey = dotenv.get("SECRET_KEY");

        // Check it, delete it later
        System.out.println(bucketName + " " + accessKey + " " +  secretKey);

        S3Client s3Client = S3Client.builder()
                .credentialsProvider(new AwsCredentialsprovider()) {
            @Override
                    public AwsCredentials reosolverCredentials() {
                return AwsBacisCredentials.builder()
                        .accessKeyID(accessKey)
                        .secretAccessKey(secretKey).build();
            }
        })
        .region(Region.EU_NORTH_1)
                .build();



        while (true){
            System.out.println("1. List alla filer");
            System.out.println("2. Ladda up fil");
            System.out.println("3. Ladda ner");
            System.out.println("4. Avsluta");
            System.out.println("Välj:");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    System.out.println("Nu listas alla filer:");
                    ListObjectsV2Request listreq = ListObjectsV2Rrequest.builder()
                            .bucket(bucketname)
                            .build();
                    listObjectsV2Response listRes = s3Client.listObjectsV2(listReq);
                     List<String> filNamnen = listRes.contents().stream()
                        .map(S3Object::key)
                        .collect(Collectors.toList());

                    break;

                case 2:
                    System.out.println("Vilka filer vill du ladda upp:");
                    break;

                case 3:
                    System.out.println("Vilka filer vill du ladda ner:");

                case 4:
                    return;
            }
        }
    }
}
