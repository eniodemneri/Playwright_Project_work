import com.github.javafaker.Faker;
import com.microsoft.playwright.*;
import org.testng.annotations.*;

public class JudgeRegisterToEvent {

    private Faker faker;

    @BeforeClass
    public void setUp() {
        faker = new Faker();
    }

    @Test
    public void testJudgeRegisterToEvent() throws InterruptedException {
        String tournamentName = "DEMO ERVIN 1";
        String eventName = "DEBATI PUBLIC";
        String tournament = "//div[h1[text()='" + tournamentName + "']]/following-sibling::div[@class='action']//a[@class='cta inverted']";

        int judgeNum = 4;
        String password = "11111111"; 

        Thread[] threads = new Thread[judgeNum]; 

        for (int i = 0; i < judgeNum; i++) {
            int index = i;

            threads[i] = new Thread(() -> {
                try (Playwright playwright = Playwright.create()) {
                    Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false));
                    BrowserContext context = browser.newContext();
                    Page page = context.newPage();

                    page.navigate("https://testing.debaterly.com/en/login");

                    String email = "judges" + (index + 1) + "test1@gmail.com";
                    page.fill("#email", email);
                    page.fill("#password", password);
                    page.click("input[type='submit']");

                    page.click(".dashNav > a:first-child");
                    page.click(tournament); 
                    page.locator(".dropdown-button").hover(); 
                    page.click("text=Register as a judge"); 
                    page.locator("#events").selectOption(eventName); 
                    page.click(".hasAcceptedTermsAndConditions"); 
                    page.click("#submit-judge-button"); 

                    System.out.println("Success " + email);

                    context.close();
                    browser.close();
                }
            });

            threads[i].start();
        }

        for (int i = 0; i < judgeNum; i++) {
            threads[i].join();
        }
    }

    @AfterClass
    public void tearDown() {
        System.out.println("End");
    }
}
