import com.microsoft.playwright.*;
import org.testng.annotations.*;

public class DebaterJoinATeam {

    private Playwright playwright;
    private Browser browser;
    private Page[] pages;
    
    @BeforeClass
    public void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium()
                .launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false));
    }

    @Test
    public void testDebaterJoinATeam() throws InterruptedException {
    	String tournamentName = "Test 3";
    	String eventName = "Congressional Debate";
    	String tournament = "//div[h1[text()='" + tournamentName + "']]/following-sibling::div[@class='action']//a[@class='cta inverted']";

    	int userNum = 1;
        String[] passwords = new String[userNum];

        for (int i = 0; i < userNum; i++) {
            passwords[i] = "11111111";
        }
        
        pages = new Page[userNum];
        for (int i = 0; i < userNum; i++) {
            BrowserContext context = browser.newContext();
            pages[i] = context.newPage();
            pages[i].navigate("https://testing.debaterly.com/en/login");

            String email = "user" + (i + 1) + "test1@gmail.com";
            pages[i].fill("#email", email);
            pages[i].fill("#password", passwords[i]);
            pages[i].click("input[type='submit']");
            pages[i].click(".dashNav > a:first-child");
            pages[i].click(tournament);
            pages[i].locator(".dropdown-button").hover();
            pages[i].click("text=Join an event");
            pages[i].locator("#events").selectOption(eventName);
            pages[i].click(".hasAcceptedTermsAndConditions");
            pages[i].click("#submit-judge-button");

            System.out.println("Success " + email);
        }
    }

    @AfterClass
    public void tearDown() {
            playwright.close();
        System.out.println("End");
    }
}
