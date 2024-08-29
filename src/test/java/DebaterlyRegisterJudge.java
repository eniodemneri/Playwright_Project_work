import com.github.javafaker.Faker;
import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import org.testng.annotations.*;

public class DebaterlyRegisterJudge {

    private Faker faker;

    @BeforeClass
    public void setUp() {
        faker = new Faker();
    }

    @Test
    public void testRegisterJudge() throws InterruptedException {
        int userNum = 4;
        String password = "11111111";

        Thread[] threads = new Thread[userNum];

        for (int i = 0; i < userNum; i++) {
            int index = i;
            threads[i] = new Thread(() -> {
                try (Playwright playwright = Playwright.create()) {
                    Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(true));
                    BrowserContext context = browser.newContext();
                    Page page = context.newPage();

                    page.navigate("https://testing.debaterly.com/en/create-account");

                    String firstName = faker.name().firstName();
                    String lastName = faker.name().lastName();
                    String email = "judges" + (index + 1) + "test1@gmail.com";

                    page.fill("#firstName", firstName);
                    page.fill("#lastName", lastName);
                    page.fill("#email", email);
                    page.fill("#password", password);
                    page.locator("#birthMonth").selectOption("7");
                    page.locator("#birthDay").selectOption("10");
                    page.locator("#birthYear").selectOption("1990");
                    page.click("#hasAcceptedTermsAndConditions");
                    page.click("#hasAcceptedPrivacyPolicy");

                    Thread.sleep(1000);
                    page.click("input[type='submit']");
                    Thread.sleep(2500);

                    page.locator("#role").selectOption("Judge");
                    page.locator("#work-school").selectOption("Yes, I’ve worked or I’m working at a school or a private club");
                    page.locator("#affiliatedTo").selectOption("school-1");
                    page.locator("#hadJudgeExperience").selectOption("Advanced ― I have significant experience in judging a debate (3+ years).");
                    page.click("input[type='submit']");

                   // PlaywrightAssertions.assertThat(page.locator("header > h1")).hasText("Dashboard");

                    System.out.println("Success " + email);

                    context.close();
                    browser.close();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            threads[i].start();
        }

        for (int i = 0; i < userNum; i++) {
            threads[i].join();
        }
    }

    @AfterClass
    public void tearDown() {
        System.out.println("End");
    }
}
