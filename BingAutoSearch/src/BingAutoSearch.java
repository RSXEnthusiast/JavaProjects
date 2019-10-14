import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;
import javax.imageio.*;

public class BingAutoSearch {

	public static int count = 1;
	public static boolean[] status = null;
	public static int curVPN = -1;

	public static void main(String[] args) {
		mainCode();
	}

	private static void mainCode() {
		// creating scanner of settings file
		File file = new File("settings.txt");
		Scanner settings = null;
		Scanner settings2 = null;
		try {
			settings = new Scanner(file);
			settings2 = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// storing settings as variables
		settings.next();
		settings2.nextLine();
		int searchNum = settings.nextInt();
		settings.next();
		settings2.nextLine();
		int mobileSearchNum = settings.nextInt();
		settings.nextLine();
		settings2.nextLine();
		settings.nextLine();
		settings2.nextLine();
		settings.nextLine();
		settings2.nextLine();
		// finding out the number of accounts
		while (settings2.hasNextLine()) {
			settings2.nextLine();
			count++;
		}
		status = new boolean[count];
		Scanner statusFile = null;
		try {
			statusFile = new Scanner(new File("accountstatus.txt"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (int i = 0; i < count; i++) {
			String temp = statusFile.nextLine();
			if (temp.equals("true")) {
				status[i] = true;
			} else if (temp.equals("false")) {
				status[i] = false;
			}
		}
		int selection = menu(file);
		// finding out if any searches have been done today
		if (selection != -2) {
			Scanner lastDone = null;
			try {
				lastDone = new Scanner(new File("lastdone.txt"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			String lastDate = lastDone.nextLine();
			// if the last done searches were done today and the user didn't override it
			// it'll start where it left off.
			if (lastDate.equals(getDate()) && selection == -1) {
				selection = lastDone.nextInt() + 1;
				if (selection > count) {
					System.out.println("Searches already done today!\n");
				}
			}
			// getting to the starting point
			for (int i = 1; i < selection; i++) {
				settings.nextLine();
			}
			long start = System.currentTimeMillis();
			int done = 0;
			if (selection > 0) {
				done = selection - 1;
			}
			int done2 = 0;
			long update = 0;
			// do searches while there's still accounts left
			while (settings.hasNextLine()) {
				done++;
				done2++;
				System.out.println("Starting on account " + done + " of " + count + ".");
				doSearches(settings.nextLine(), searchNum, mobileSearchNum, done);
				update = System.currentTimeMillis();
				System.out.println("\nCurrent time: " + (Math.round((update - start) / 1000.0 / 60.0)) + "m");
				System.out.println("Estimated time remaining: "
						+ (Math.round((((update - start) / done2) * (count - done2)) / 1000.0 / 60.0)) + "m\n");
			}
			long end = System.currentTimeMillis();
			System.out.println("Total time took: " + Math.round(((end - start) / 1000.0 / 60.0)) + "m");
		}
		try {
			FileWriter fw = new FileWriter("accountstatus.txt", false);
			fw.write(accountStatusToStringRaw());
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static String accountStatusToStringRaw() {
		String result = "";
		for (int i = 0; i < status.length; i++) {
			if (status[i]) {
				result += "true\n";
			} else {
				result += "false\n";
			}
		}
		return result;
	}

	private static String accountBlockedToStringPretty() {
		String result = "Blocked: ";
		int i = 0;
		for (i = 0; i < (status.length) - 1; i++) {
			if (!status[i]) {
				result += (i + 1) + " ";
			}
		}
		if (result.equals("Blocked: ")) {
			result = "All accounts are open!";
		}
		result += "\n";
		return result;
	}

	private static int menu(File file) {
		Scanner kin = new Scanner(System.in);
		while (true) {
			Scanner scanner = null;
			try {
				scanner = new Scanner(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			scanner.nextLine();
			scanner.nextLine();
			scanner.nextLine();
			System.out.println("0 - Run/resume the program");
			System.out.println("N - New Account");
			System.out.println("1-" + count + " - View account's reward page");
			System.out.println("E+1-" + count + " - View account's email");
			System.out.println("O+1-" + count + " - View account's past orders");
			System.out.println("S+1-" + count + " - Start at a different account");
			System.out.println("AS - Check last known account status.");
			System.out.println("Q - Quit");
			System.out.print("Make a selection: ");
			String selection = kin.next();
			System.out.println();
			selection = selection.toUpperCase();
			if (selection.equals("Q")) {
				System.out.println("Later!");
				return -2;
			} else if (selection.equals("AS")) {
				System.out.println(accountBlockedToStringPretty());
			} else if (selection.equals("N")) {
				newAccount();
			} else if (selection.equals("0")) {
				break;
			} else if (selection.substring(0, 1).equals("E")) {
				try {
					int account = Integer.parseInt(selection.substring(1));
					for (int i = 0; i < account; i++) {
						scanner.nextLine();
					}
					if (account <= count) {
						login(scanner.nextLine(), account);
						boolean flag = false;
						while (true) {
							if (isOnScreen(new File("4-bing.png"))) {
								flag = true;
								break;
							} else if (isOnScreen(new File("2-locked.png"))
									|| isOnScreen(new File("2-locked720p.png"))) {
								break;
							}
						}
						if (flag == true) {
							waitForImage(new File("4-bing.png"));
							press(KeyEvent.VK_ALT);
							press(KeyEvent.VK_D);
							wait(10);
							release(KeyEvent.VK_D);
							release(KeyEvent.VK_ALT);
							wait(100);
							type("outlook.com");
							wait(100);
							tap(KeyEvent.VK_ENTER);
						} else {
							press(KeyEvent.VK_ALT);
							press(KeyEvent.VK_F4);
							wait(10);
							release(KeyEvent.VK_F4);
							release(KeyEvent.VK_ALT);
							System.out.println("That account has been locked, please try again later\n");
						}
					} else {
						System.out.println("You don't have that many accounts.");
						System.out.println();
					}
				} catch (NumberFormatException e) {

				}
			} else if (selection.substring(0, 1).equals("O")) {
				try {
					int account = Integer.parseInt(selection.substring(1));
					for (int i = 0; i < account; i++) {
						scanner.nextLine();
					}
					if (account <= count) {
						login(scanner.nextLine(), account);
						boolean flag = false;
						while (true) {
							if (isOnScreen(new File("4-bing.png"))) {
								flag = true;
								break;
							} else if (isOnScreen(new File("2-locked.png"))
									|| isOnScreen(new File("2-locked720p.png"))) {
								break;
							}
						}
						if (flag == true) {
							press(KeyEvent.VK_ALT);
							press(KeyEvent.VK_D);
							wait(10);
							release(KeyEvent.VK_D);
							release(KeyEvent.VK_ALT);
							wait(100);
							type("https://account.microsoft.com/rewards/redeem/orderhistory");
							wait(100);
							tap(KeyEvent.VK_ENTER);
						} else {
							press(KeyEvent.VK_ALT);
							press(KeyEvent.VK_F4);
							wait(10);
							release(KeyEvent.VK_F4);
							release(KeyEvent.VK_ALT);
							System.out.println("That account has been locked, please try again later\n");
						}
					} else {
						System.out.println("You don't have that many accounts.");
						System.out.println();
					}
				} catch (NumberFormatException e) {

				}
			} else if (selection.substring(0, 1).equals("S")) {
				int result = Integer.parseInt(selection.substring(1));
				if (result > 0 && result <= count) {
					return result;
				} else {
					System.out.println("You dont have that many accounts.\n");
				}
			} else {
				try {
					int account = Integer.parseInt(selection);
					for (int i = 0; i < account; i++) {
						scanner.nextLine();
					}
					if (account <= count) {
						login(scanner.nextLine(), account);
						boolean flag = false;
						while (true) {
							if (isOnScreen(new File("4-bing.png"))) {
								flag = true;
								break;
							} else if (isOnScreen(new File("2-locked.png"))
									|| isOnScreen(new File("2-locked720p.png"))) {
								break;
							}
						}
						if (flag == true) {
							press(KeyEvent.VK_ALT);
							press(KeyEvent.VK_D);
							wait(10);
							release(KeyEvent.VK_D);
							release(KeyEvent.VK_ALT);
							wait(100);
							type("https://account.microsoft.com/rewards/dashboard");
							wait(100);
							tap(KeyEvent.VK_ENTER);
						} else {
							press(KeyEvent.VK_ALT);
							press(KeyEvent.VK_F4);
							wait(10);
							release(KeyEvent.VK_F4);
							release(KeyEvent.VK_ALT);
							System.out.println("That account has been locked, please try again later\n");
						}
					} else {
						System.out.println("You don't have that many accounts.");
						System.out.println();
					}
				} catch (NumberFormatException e) {

				}
			}
		}
		kin.close();
		return -1;
	}

	private static void newAccount() {
		try {
			Runtime.getRuntime().exec("taskkill /F /IM msedge.exe");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		wait(500);
		try {
			Runtime.getRuntime()
					.exec("C:\\Users\\joelk\\AppData\\Local\\Microsoft\\Edge SxS\\Application\\msedge.exe -inprivate");
		} catch (IOException e) {
			e.printStackTrace();
		}
		// waits for chrome to open
		waitForImage(new File("1-bing.png"));
		// Going to login page
		type("https://login.live.com/login.srf?wa=wsignin1.0&rpsnv=13&ct=1558233928&rver=6.7.6631.0&wp=MBI_SSL&wreply=https%3a%2f%2fwww.bing.com%2fsecure%2fPassport.aspx%3frequrl%3dhttps%253a%252f%252fwww.bing.com%252f%253ftoWww%253d1%2526redig%253dB9B52DC23E164AFAA2FE9ECDBB99B1FD%2526wlexpsignin%253d1%26sig%3d15C6D81505E069E83B72D574045368EB&lc=1033&id=264960&CSRFToken=da786375-afcf-4d7b-a22a-a3ec54c9e0e5&aadredir=1");
		tap(KeyEvent.VK_ENTER);
		waitForImage(new File("1-new.png"));
		wait(500);
		// changes to windows if not already
		while (!isOnScreen(new File("user-agent_default.png"))) {
			changeToWindows();
			wait(500);
		}
		File one = new File("1-new.png");
		// waits for page to load
		waitForImage(one);
		// finds where it needs to click "create one"
		int[] a = findSubImage(one);
		// clicks on "create one"
		while (isOnScreen(one)) {
			click(a[0], a[1]);
		}
		File two = new File("2-new.png");
		// waits for page to load
		waitForImage(two);
		// finds where to click "get new email"
		a = findSubImage(two);
		// clicks "get new email"
		click(a[0], a[1]);
		// waits for page to load
		waitForImage(new File("3-new.png"));
		// typing new email desired name
		String temp = "testing" + (count + 1);
		type(temp);
		tap(KeyEvent.VK_ENTER);
		File four = new File("4-new.png");
		// waiting for and clicking on choose different similar name
		waitForImage(four);
		a = findSubImage(four);
		click(a[0], a[1]);
		// waiting for page to load
		File five = new File("5-new.png");
		waitForImage(five);
		wait(500);
		// clicking on new account name
		a = findSubImage(five);
		click(a[0], a[1]);
		// waits for page to load
		waitForImage(new File("6-new.png"));
		wait(500);
		// selects email
		press(KeyEvent.VK_CONTROL);
		press(KeyEvent.VK_A);
		wait(100);
		release(KeyEvent.VK_A);
		wait(100);
		// copies email
		press(KeyEvent.VK_C);
		wait(10);
		release(KeyEvent.VK_C);
		release(KeyEvent.VK_CONTROL);
		wait(100);
		// retrieves email from clipboard
		Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable clipTf = sysClip.getContents(null);
		String nextLine = "\n";
		if (clipTf != null) {
			if (clipTf.isDataFlavorSupported(DataFlavor.stringFlavor)) {
				try {
					nextLine += (String) clipTf.getTransferData(DataFlavor.stringFlavor);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		tap(KeyEvent.VK_ENTER);
		// waits for page to load
		waitForImage(new File("7-new.png"));
		// password
		type("always1st");
		tap(KeyEvent.VK_ENTER);
		// adds new email and password to file
		nextLine += "@outlook.com always1st";
		waitForImage(new File("8-new.png"));
		type(temp);
		wait(100);
		tap(KeyEvent.VK_TAB);
		wait(100);
		type("account");
		wait(100);
		tap(KeyEvent.VK_ENTER);
		waitForImage(new File("9-new.png"));
		wait(100);
		tap(KeyEvent.VK_TAB);
		wait(50);
		tap(KeyEvent.VK_ENTER);
		wait(50);
		Random rand = new Random();
		int month = rand.nextInt(12) + 1;
		int day = rand.nextInt(30) + 1;
		int year = rand.nextInt(60) + 20;
		for (int i = 0; i < month; i++) {
			tap(KeyEvent.VK_DOWN);
			wait(10);
		}
		tap(KeyEvent.VK_ENTER);
		wait(50);
		tap(KeyEvent.VK_TAB);
		wait(50);
		tap(KeyEvent.VK_ENTER);
		wait(50);
		for (int i = 0; i < day; i++) {
			tap(KeyEvent.VK_DOWN);
			wait(10);
		}
		tap(KeyEvent.VK_ENTER);
		wait(50);
		tap(KeyEvent.VK_TAB);
		wait(50);
		tap(KeyEvent.VK_ENTER);
		wait(50);
		for (int i = 0; i < year; i++) {
			tap(KeyEvent.VK_DOWN);
			wait(10);
		}
		tap(KeyEvent.VK_ENTER);
		wait(50);
		tap(KeyEvent.VK_TAB);
		wait(50);
		tap(KeyEvent.VK_ENTER);
		wait(50);
		waitForImage(new File("10-new.png"));
		wait(250);
		type("5093590534");
		wait(50);
		a = findSubImage(new File("11-new.png"));
		wait(50);
		click(a[0], a[1]);
		boolean wasSuc = true;
		wait(2000);
		// waiting for screen to change
		while (!isOnScreen(new File("12-new.png")) && isOnScreen(new File("13-new.png"))) {
			wait(500);
		}
		// if failed
		if (isOnScreen(new File("13-new.png"))) {
			wasSuc = false;
		}
		if (wasSuc) {
			System.out.println("Yay! More money! Account creation successful!");
			try {
				Files.write(Paths.get("settings.txt"), nextLine.getBytes(), StandardOpenOption.APPEND);
				Files.write(Paths.get("accountstatus.txt"), "true".getBytes(), StandardOpenOption.APPEND);
				status = addArray(status, true);
			} catch (IOException e) {
			}
			count++;
		} else {
			System.out.println("Account creation not successful. Better luck next time.");
		}
		wait(1000);
		press(KeyEvent.VK_ALT);
		press(KeyEvent.VK_F4);
		wait(10);
		release(KeyEvent.VK_F4);
		release(KeyEvent.VK_ALT);
		System.out.println();
	}

	private static boolean[] addArray(boolean[] status2, boolean b) {
		boolean[] result = new boolean[status2.length + 1];
		for (int i = 0; i < status2.length; i++) {
			result[i] = status2[i];
		}
		result[status2.length] = b;
		return result;
	}

	private static String getDate() {
		Date today = Calendar.getInstance().getTime();
		return today.toString().substring(4, 10) + today.toString().substring(23);
	}

	private static void login(String nextLine, int accountNum) {
		Scanner scanner = new Scanner(nextLine);
		String email = scanner.next();
		String password = scanner.next();
		String[] VPNLocs = new String[13];
		Scanner statusFile = null;
		try {
			statusFile = new Scanner(new File("vpnserverlocations.txt"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (int i = 0; i < 13; i++) {
			VPNLocs[i] = statusFile.next();
		}
		if ((accountNum - 1) / 4 != curVPN) {
			changeVpnLoc(VPNLocs[(accountNum - 1) / 4]);
			curVPN = (accountNum - 1) / 4;
		}
		try {
			Runtime.getRuntime().exec("taskkill /F /IM msedge.exe");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		wait(500);
		try {
			Runtime.getRuntime()
					.exec("C:\\Users\\joelk\\AppData\\Local\\Microsoft\\Edge SxS\\Application\\msedge.exe -inprivate");
		} catch (IOException e) {
			e.printStackTrace();
		}
		// waits for chrome to open
		waitForImage(new File("1-bing.png"));
		// Going to login page
		type("https://login.live.com/login.srf?wa=wsignin1.0&rpsnv=13&ct=1558233928&rver=6.7.6631.0&wp=MBI_SSL&wreply=https%3a%2f%2fwww.bing.com%2fsecure%2fPassport.aspx%3frequrl%3dhttps%253a%252f%252fwww.bing.com%252f%253ftoWww%253d1%2526redig%253dB9B52DC23E164AFAA2FE9ECDBB99B1FD%2526wlexpsignin%253d1%26sig%3d15C6D81505E069E83B72D574045368EB&lc=1033&id=264960&CSRFToken=da786375-afcf-4d7b-a22a-a3ec54c9e0e5&aadredir=1");
		tap(KeyEvent.VK_ENTER);
		waitForImage(new File("1-new.png"));
		wait(500);
		// changes to windows if not already
		while (!isOnScreen(new File("user-agent_default.png"))) {
			changeToWindows();
			wait(500);
		}
		wait(500);
		// waits for login page to open
		waitForImage(new File("2-bing.png"));
		// typing email
		type(email);
		wait(100);
		tap(KeyEvent.VK_ENTER);
		// waits for password page
		wait(5000);
		// typing password
		type(password);
		wait(100);
		tap(KeyEvent.VK_ENTER);
		wait(2000);
		while (true) {
			if (isOnScreen(new File("4-bing.png"))) {
				break;
			} else if (isOnScreen(new File("phone.png"))) {
				wait(500);
				type("5098347674");
				wait(100);
				tap(KeyEvent.VK_ENTER);
				wait(500);
				break;
			} else if (isOnScreen(new File("1-locked.png"))) {
				wait(500);
				tap(KeyEvent.VK_ENTER);
				int[] a = null;
				while (true) {
					if (isOnScreen(new File("2-lockedclick.png"))) {
						a = findSubImage(new File("2-lockedclick.png"));
						break;
					} else if (isOnScreen(new File("2-lockedclick720p.png"))) {
						a = findSubImage(new File("2-lockedclick720p.png"));
						break;
					}
				}
				click(a[0], a[1] + 40);
				wait(500);
				type("5093590534");
				wait(500);
				while (true) {
					if (isOnScreen(new File("3-lockedclick.png"))) {
						a = findSubImage(new File("3-lockedclick.png"));
						break;
					} else if (isOnScreen(new File("3-lockedclick720p.png"))) {
						a = findSubImage(new File("3-lockedclick720p.png"));
						break;
					}
				}
				click(a[0], a[1]);
				wait(500);
				break;
			} else if (isOnScreen(new File("6-bing.png"))) {
				int[] a = findSubImage(new File("6-bing.png"));
				click(a[0], a[1]);
				wait(500);
				tap(KeyEvent.VK_TAB);
				wait(500);
				type("0534");
				wait(500);
				tap(KeyEvent.VK_ENTER);
			}
		}
		while (true) {
			if (isOnScreen(new File("4-bing.png"))) {
				status[accountNum - 1] = true;
				break;
			} else if (isOnScreen(new File("2-locked720p.png")) || isOnScreen(new File("2-locked.png"))) {
				status[accountNum - 1] = false;
				break;
			}
		}
		try {
			FileWriter fw = new FileWriter("accountstatus.txt", false);
			fw.write(accountStatusToStringRaw());
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		scanner.close();
	}

	private static void doSearches(String nextLine, int searchNum, int mobileSearchNum, int done) {
		login(nextLine, done);
		boolean flag = false;
		while (true) {
			if (isOnScreen(new File("4-bing.png"))) {
				flag = true;
				break;
			} else if (isOnScreen(new File("2-locked720p.png")) || isOnScreen(new File("2-locked.png"))) {
				break;
			}
		}
		// running searches
		if (flag == true) {
			int searchesLeft = searchNum + mobileSearchNum + searchNum / 7 + mobileSearchNum / 7;
			System.out.println("Searches left: " + searchesLeft);
			searchesLeft = search(searchNum, searchesLeft, done);
			while (!isOnScreen(new File("user-agent_android.png"))) {
				changeToAndroid();
				wait(500);
			}
			if (mobileSearchNum > 0) {
				searchesLeft = search(mobileSearchNum, searchesLeft, done);
			}
		}
		Scanner lastDone = null;
		try {
			lastDone = new Scanner(new File("lastdone.txt"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String lastDoneDate = lastDone.nextLine();
		int lastDoneNum = lastDone.nextInt();
		if ((lastDoneDate.equals(getDate()) && done > lastDoneNum) || !lastDoneDate.equals(getDate())) {
			try {
				FileWriter fw = new FileWriter("lastdone.txt", false);
				fw.write(getDate());
				fw.write("\n" + done);
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// closing window
		press(KeyEvent.VK_ALT);
		press(KeyEvent.VK_F4);
		wait(10);
		release(KeyEvent.VK_F4);
		release(KeyEvent.VK_ALT);
	}

	private static int search(int searchNum, int searchesLeft, int done) {
		ArrayList<String> a = new ArrayList<String>();
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File("wordlist.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (scanner.hasNext()) {
			a.add(scanner.next());
		}
		Random rand = new Random();
		for (int i = 0; i < searchNum + (searchNum / 7); i++) {
			// navigating to searchbar
			press(KeyEvent.VK_ALT);
			press(KeyEvent.VK_D);
			wait(10);
			release(KeyEvent.VK_D);
			release(KeyEvent.VK_ALT);
			wait(400);
			// typing random combo of two words
			type(a.get(rand.nextInt(a.size())) + " " + a.get(rand.nextInt(a.size())));
			wait(100);
			tap(KeyEvent.VK_ENTER);
			// waiting for page to load
			waitForImage(new File("5-bing.png"));
			searchesLeft--;
			System.out.println(searchesLeft + " searches left on account " + done + " of " + count);
			wait(1000);
		}
		scanner.close();
		return searchesLeft;
	}

	private static void changeVpnLoc(String string) {
		tap(KeyEvent.VK_WINDOWS);
		int[] a = null;
		waitForImage(new File("vpn1.png"));
		a = findSubImage(new File("vpn1.png"));
		click(a[0], a[1]);
		waitForImage(new File("vpn2.png"));
		a = findSubImage(new File("vpn2.png"));
		click(a[0], a[1]);
		wait(1000);
		tap(KeyEvent.VK_TAB);
		tap(KeyEvent.VK_TAB);
		tap(KeyEvent.VK_TAB);
		type(string);
		tap(KeyEvent.VK_ENTER);
		wait(1000);
		waitForImage(new File("vpn3.png"));
		a = findSubImage(new File("vpn3.png"));
		click(a[0], a[1]);
		waitForImage(new File("vpn4.png"));
		wait(1000);
		while (isOnScreen(new File("vpn5.png"))) {
			// empty while
		}
	}

	private static void waitForImage(File file) {
		wait(200);
		while (true) {
			if (isOnScreen(file)) {
				break;
			}
		}
		wait(200);
	}

	private static void changeToAndroid() {
		int[] a = findSubImage(new File("user-agent_default.png"));
		if (a[0] == -1) {
			return;
		}
		int x = a[0];
		int y = a[1];
		// clicking icon
		click(x, y);
		// moving down and left to click "android"
		x -= 225;
		y += 100;
		wait(500);
		click(x, y);
		// moving up to click "kitkat"
		y -= 70;
		wait(500);
		click(x, y);
		wait(200);
		tap(KeyEvent.VK_ESCAPE);
		wait(200);
	}

	private static void changeToWindows() {
		int[] a = findSubImage(new File("user-agent_android.png"));
		if (a[0] == -1) {
			return;
		}
		int x = a[0];
		int y = a[1];
		// clicking icon
		click(x, y);
		// moving down and left to click "chrome"
		x -= 225;
		y += 30;
		wait(500);
		click(x, y);
		// clicking "default"
		wait(500);
		click(x, y);
		wait(200);
		tap(KeyEvent.VK_ESCAPE);
		wait(200);
	}

	private static void moveMouse(int x, int y) {
		Robot r = null;
		try {
			r = new Robot();
		} catch (AWTException e1) {
			e1.printStackTrace();
		}

		r.mouseMove(x, y);
	}

	private static void click(int x, int y) {
		Robot r = null;
		try {
			r = new Robot();
		} catch (AWTException e1) {
			e1.printStackTrace();
		}

		r.mouseMove(x, y);
		wait(100);
		r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}

	private static void release(int vk) {
		Robot r = null;
		try {
			r = new Robot();
		} catch (AWTException e1) {
			e1.printStackTrace();
		}

		r.keyRelease(vk);
	}

	private static void press(int vk) {
		Robot r = null;
		try {
			r = new Robot();
		} catch (AWTException e1) {
			e1.printStackTrace();
		}

		r.keyPress(vk);
	}

	private static void wait(int i) {
		try {
			TimeUnit.MILLISECONDS.sleep(i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void tap(int vk) {
		Robot r = null;
		try {
			r = new Robot();
		} catch (AWTException e1) {
			e1.printStackTrace();
		}

		r.keyPress(vk);
		r.keyRelease(vk);
	}

	private static void type(String text) {
		// putting string into clipboard
		StringSelection stringSelection = new StringSelection(text);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, stringSelection);
		Robot r = null;
		try {
			r = new Robot();
		} catch (AWTException e1) {
			e1.printStackTrace();
		}
		// pasting string
		r.keyPress(KeyEvent.VK_CONTROL);
		r.keyPress(KeyEvent.VK_V);
		r.keyRelease(KeyEvent.VK_V);
		r.keyRelease(KeyEvent.VK_CONTROL);
	}

	private static boolean isOnScreen(File picture) {
		int[] a = findSubImage(picture);
		if (a[0] == -1) {
			return false;
		} else {
			return true;
		}
	}

	private static int[] findSubImage(File picture) {
		// creating robot
		Robot r = null;
		try {
			r = new Robot();
		} catch (AWTException e1) {
			e1.printStackTrace();
		}
		// getting screen size
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// creating new rectangle of size screensize
		Rectangle rectangle = new Rectangle();
		rectangle.setBounds(0, 0, ((Double) screenSize.getWidth()).intValue(),
				((Double) screenSize.getHeight()).intValue());
		// robot is taking a screenshot
		BufferedImage screen = r.createScreenCapture(rectangle);
		// scanning in smaller image
		BufferedImage icon = null;
		try {
			icon = ImageIO.read(picture);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// looping through height of screenshot
		for (int bigY = 0; bigY < screen.getHeight(); bigY++) {
			// looping through width of screen
			for (int bigX = 0; bigX < screen.getWidth(); bigX++) {
				// if current pixel is same as first pixel of icon
				if (screen.getRGB(bigX, bigY) == icon.getRGB(0, 0)) {
					// assume image is correct
					boolean isCorrect = true;
					// looping through width of image
					for (int smallY = 0; smallY < icon.getHeight(); smallY += 4) {
						// looping through height of image
						for (int smallX = 2; smallX < icon.getWidth(); smallX += 4) {
							// try to check the rest of the pixels, if it goes off screen it'll catch the
							// error
							try {
								// if current screenshot pixel is not equal to current image pixel
								if (screen.getRGB(bigX + smallX, bigY + smallY) != icon.getRGB(smallX, smallY)) {
									// set flag to false and break
									isCorrect = false;
									break;
								}
							} catch (ArrayIndexOutOfBoundsException e) {
								// set flag to false and break
								isCorrect = false;
								break;
							}
						}
						// breaking again if we know image is incorrect
						if (!isCorrect) {
							break;
						}
					}
					// if image is at that location
					if (isCorrect) {
						// return center of location of image
						return new int[] { bigX + (icon.getWidth() / 2), bigY + (icon.getHeight() / 2) };
					}
				}
			}
		}
		// if image wasn't found return empty array
		return new int[] { -1, -1 };
	}
}