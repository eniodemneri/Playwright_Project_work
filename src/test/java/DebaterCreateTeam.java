import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class DebaterCreateTeam {

	public static void main(String[] args) throws InterruptedException {

		Playwright playwright = Playwright.create();
		Browser browser = playwright.chromium()
				.launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false));

		
		String[] emails = { "user1+25@gmail.com", "user2+27@gmail.com"};
		String[] institution = { "school-2", "private club-1"}; //"school-1", "school-2", "private club-1", "school 15", "Schools2"
		String[] teams = { "Team AB 8", "Team AB 9"};
		
		String tournament_name = "ATL High School state champ";
		String event_name = "Event A";
		String tournament = "//div[h1[text()='"+tournament_name+"']]/following-sibling::div[@class='action']//a[@class='cta inverted']";
		System.out.println("Tournament: "+tournament);
		//"//div[h1[text()='ATL High School state champ']]/following-sibling::div[@class='action']//a[@class='cta inverted']"
		//"section.upcomings > div:nth-child(4) > article > div.action > a";

		int userNum = emails.length;
		String[] password = new String[userNum];
		
		for (int i = 0; i < userNum; i++) {
			password[i] = "11111111";
		}
		Page[] page = new Page[userNum];
		for (int i = 0; i < userNum; i++) {
			BrowserContext context = browser.newContext();
			page[i] = context.newPage();
		}

		for (int i = 0; i < userNum; i++) {
			page[i].navigate("https://debaterly.snet.al/en/login");
		}
		for (int i = 0; i < userNum; i++) {
			page[i].fill("#email", emails[i]);
			page[i].fill("#password", password[i]);
			// Thread.sleep(10000);
			page[i].click("input[type='submit']");
			page[i].click(".dashNav > a:first-child");
			page[i].click(tournament);
			page[i].locator(".dropdown-button").hover();
			page[i].click("text=Create a team");
			page[i].locator("#event").selectOption(event_name);
			page[i].locator("#managedInstitution").selectOption(institution[i]);
			page[i].fill("#teamName", teams[i]);
			page[i].click("#submit-team-button"); 
			page[i].fill("#memberEmail", "newmemeber@test.com");
			page[i].click("#addTeamButton");
			page[i].click("#submit-team-button");

			FrameLocator stripeFrame = page[i].frameLocator("iframe[name^='__privateStripeFrame']");
			stripeFrame.locator("input[name='cardnumber']").fill("4242424242424242");
			stripeFrame.locator("input[name='exp-date']").fill("1234");
			stripeFrame.locator("input[name='cvc']").fill("909");
			stripeFrame.locator("input[name='postal']").fill("10001");

			page[i].click("#hasAcceptedTermsAndConditions");
			page[i].click("#hasAcceptedRefundPolicy");
			page[i].click("#submit-team-button");
			//assertThat(page[i].locator(".js-step-title")).hasText("Confirmation");
		}
		playwright.close();
		System.out.println("Success");
	}

}
