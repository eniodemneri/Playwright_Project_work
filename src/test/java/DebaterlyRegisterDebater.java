import com.github.javafaker.Faker;
import com.microsoft.playwright.*;
import org.testng.annotations.*;

public class DebaterlyRegisterDebater {
    private Faker faker;

    @BeforeClass
    public void setUp() {
        faker = new Faker();
    }

    @Test
    public void testRegisterDebater() throws InterruptedException {
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
                    String email = "teams" + (index + 1) + "test1@gmail.com";

                    page.fill("#firstName", firstName);
                    System.out.println(firstName);
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
                    Thread.sleep(1500);

                    page.locator("#role").selectOption("Student Competitor");
                    //page.locator("#school").selectOption("school-1");
                    page.click("input[type='submit']");

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









/*import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.github.javafaker.Faker;
import org.testng.annotations.*;
public class DebaterlyRegisterDebater {
    private Playwright playwright;
    private Browser browser;
    private Page[] pages;
    private Faker faker;
    @BeforeClass
    public void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(true));
        faker = new Faker();
    }
    @Test
    public void testRegisterDebater() throws InterruptedException {
        int userNum = 4;
        String[] passwords = new String[userNum];
        pages = new Page[userNum];
        for (int i = 0; i < userNum; i++) {
            passwords[i] = "11111111";
        }
        for (int i = 0; i < userNum; i++) {
            BrowserContext context = browser.newContext();
            pages[i] = context.newPage();
        }
        for (int i = 0; i < userNum; i++) {
            pages[i].navigate("https://testing.debaterly.com/en/create-account");
        }
        for (int i = 0; i < userNum; i++) {
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = "user" + (i + 1) + "test1@gmail.com";
            pages[i].fill("#firstName", firstName);
            System.out.println(firstName);
            pages[i].fill("#lastName", lastName);
            pages[i].fill("#email", email);
            pages[i].fill("#password", passwords[i]);
            pages[i].locator("#birthMonth").selectOption("7");
            pages[i].locator("#birthDay").selectOption("10");
            pages[i].locator("#birthYear").selectOption("1990");
            pages[i].click("#hasAcceptedTermsAndConditions");
            pages[i].click("#hasAcceptedPrivacyPolicy");
            Thread.sleep(1000);
            pages[i].click("input[type='submit']");
            Thread.sleep(1500);
            pages[i].locator("#role").selectOption("Student Competitor");
            pages[i].locator("#school").selectOption("school-1");
            pages[i].click("input[type='submit']");
           // PlaywrightAssertions.assertThat(pages[i].locator("header > h1")).hasText("Dashboard");
            System.out.println("Success " + email);
        }
    }
    @AfterClass
    public void tearDown() {
        System.out.println("End");
    }
}*/
