package spas.usercontrol;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Recur;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.model.TimeZoneRegistry;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.model.property.Organizer;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.RRule;
import net.fortuna.ical4j.model.property.Transp;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.UidGenerator;
import spas.nhandling.NReader;
import spas.nhandling.nelements.NCourse;
import spas.nhandling.nelements.NEvent;

/**
 * Class for creating an iCalendar representation of user's courses' events.
 * 
 * @author Lauri Lavanti
 * @version 1.2
 * @since 0.2
 * 
 */
public class CalendarCreator {
	private UserCourseHandler handler;
	private NReader nreader = new NReader();
	File userfile;

	/**
	 * Constructor for a CalendarCreator. Nothing special here.
	 * 
	 * @param userpath
	 *            The absolute path to user's resource file.
	 * @param icspath
	 *            The absolute path to the .ics file for user.
	 */
	public CalendarCreator(String userpath, String icspath) {
		handler = new UserCourseHandler(userpath);
		userfile = new File(icspath);
	}

	/**
	 * Creates the iCalendar-representation of user's courses' events.
	 * 
	 * @return <code>true</code> if nothing went wrong with the creation of
	 *         calendar.
	 */
	public boolean createCalendar() {
		// Create a TimeZone
		TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance()
				.createRegistry();
		TimeZone timezone = registry.getTimeZone("Europe/Helsinki");
		VTimeZone tz = timezone.getVTimeZone();

		// Create a transparency.
		Transp transp = Transp.OPAQUE;

		// Create the calendar, and insert some basic properties to it.
		Calendar calendar = new Calendar();
		calendar.getProperties().add(new ProdId("-//Lauri Lavanti//SPAS//FI"));
		calendar.getProperties().add(Version.VERSION_2_0);
		calendar.getProperties().add(CalScale.GREGORIAN);
		calendar.getProperties().add(Method.PUBLISH);

		// Create the uidgenerator.
		UidGenerator ug = null;
		try {
			ug = new UidGenerator(Long.toHexString(Double.doubleToLongBits(Math
					.random())));

			// Get current period and current year.
			String currentperiod = getCurrentPeriod();
			int currentyear = java.util.Calendar.getInstance().get(
					java.util.Calendar.YEAR);

			// Get user's courses and loop through them all.
			List<NCourse> courses = handler.getCourses();
			for (NCourse c : courses) {

				// Get periods.
				String[] periods = c.getExecperiod().split(" - ");
				// Because teachers don't have a standard.
				if (periods.length == 1) {
					periods = periods[0].split("-");
				}

				for (String period : periods) {
					// Because teacher's don't have a standard.
					if (period.contains("IV")) {
						period = "IV";
					} else if (period.contains("III")) {
						period = "III";
					} else if (period.contains("II")) {
						period = "II";
					} else if (period.contains("I")) {
						period = "I";
					}

					// Make sure course is planned for current period and year.
					if (period.equals(currentperiod)
							&& c.getExecyear() == currentyear) {

						// Get group for course.
						String group = handler.getGroup(c.getId());

						// Get lectures for course and loop through them all.
						List<NEvent> lectures = nreader.getCourseLectures(c);
						for (NEvent event : lectures) {
							VEvent vevent = createLecture(event);
							// Add basic properties to VEvent.
							vevent.getProperties().add(tz.getTimeZoneId());
							vevent.getProperties().add(transp);
							vevent.getProperties().add(ug.generateUid());
							vevent.getProperties().add(new Organizer());

							// Add VEvent to calendar.
							calendar.getComponents().add(vevent);
						}

						// Get exercises for course and loop through them all.
						List<NEvent> exercises = nreader.getCourseExercises(c);
						for (NEvent event : exercises) {
							// Make sure exercises of a wrong group aren't
							// added.
							if (group.equals("")
									|| (!group.equals("") && group.equals(event
											.getGroup()))) {
								VEvent vevent = createExercise(event);
								// Add basic properties to VEvent.
								vevent.getProperties().add(tz.getTimeZoneId());
								vevent.getProperties().add(transp);
								vevent.getProperties().add(ug.generateUid());
								vevent.getProperties().add(new Organizer());

								// Add VEvent to calendar.
								calendar.getComponents().add(vevent);
							}
						}

						// Get assignments for course and loop through them all.
						List<NEvent> assignments = nreader
								.getCourseAssignments(c);
						for (NEvent event : assignments) {
							VEvent vevent = createAssignment(event);
							// Add basic properties to VEvent.
							vevent.getProperties().add(tz.getTimeZoneId());
							vevent.getProperties().add(transp);
							vevent.getProperties().add(ug.generateUid());
							vevent.getProperties().add(new Organizer());

							// Add VEvent to calendar.
							calendar.getComponents().add(vevent);
						}

						// Get events for course and loop through them all.
						List<NEvent> events = nreader.getCourseEvents(c);
						for (NEvent event : events) {
							VEvent vevent = createEvent(event);
							// Add basic properties to VEvent.
							vevent.getProperties().add(tz.getTimeZoneId());
							vevent.getProperties().add(transp);
							vevent.getProperties().add(ug.generateUid());
							vevent.getProperties().add(new Organizer());

							// Add VEvent to calendar.
							calendar.getComponents().add(vevent);
						}
					}
				}
			}

			// Finally saving the calendar to file.
			FileOutputStream fout = new FileOutputStream(userfile);

			CalendarOutputter outputter = new CalendarOutputter();
			outputter.output(calendar, fout);

			return true;

		} catch (ValidationException | IOException ex) {
			// These shouldn't happen normally.
		}
		return false;
	}

	/**
	 * Method for making a VEvent object out of NEvent lecture.
	 * 
	 * @param lecture
	 *            NEvent to change into a VEvent.
	 * @return VEvent representation of given NEvent.
	 */
	private static VEvent createLecture(NEvent lecture) {
		// Make starting and ending date for lecture.
		DateTime start = new DateTime(lecture.getStartDate().getTime());
		DateTime end = new DateTime(lecture.getEndDate().getTime());

		// Make summary, location and description.
		String summary = lecture.getName() + " luento";
		Description desc = new Description(lecture.getDescription());
		Location loc = new Location(lecture.getLocation());

		// Make VEvent and add properties to it.
		VEvent vle = new VEvent(start, end, summary);
		vle.getProperties().add(loc);
		vle.getProperties().add(desc);

		return vle;
	}

	/**
	 * Method for making a VEvent object out of NEvent exercise.
	 * 
	 * @param exercise
	 *            NEvent to change into a VEvent.
	 * @return VEvent representation of given NEvent.
	 */
	private static VEvent createExercise(NEvent exercise) {
		// Make starting and ending dates for exercise.
		java.util.Calendar startc = exercise.getStartDate();
		java.util.Calendar endc = exercise.getEndDate();
		java.util.Calendar firstend = startc;
		firstend.set(java.util.Calendar.HOUR, endc.get(java.util.Calendar.HOUR));
		firstend.set(java.util.Calendar.MINUTE,
				endc.get(java.util.Calendar.MINUTE));
		DateTime start = new DateTime(startc.getTime());
		DateTime end = new DateTime(firstend.getTime());

		// Make summary and location.
		String summary = exercise.getName() + " " + exercise.getGroup()
				+ " harjoitus";
		Location loc = new Location(exercise.getLocation());

		// Make VEvent and add properties to it.
		VEvent vex = new VEvent(start, end, summary);
		vex.getProperties().add(loc);

		// Make the ending time of recurrence and add it.
		endc.add(java.util.Calendar.HOUR, 12);
		RRule recurrence = new RRule(new Recur(Recur.WEEKLY, new DateTime(
				endc.getTime())));
		vex.getProperties().add(recurrence);

		return vex;
	}

	/**
	 * Method for making a VEvent object out of NEvent event.
	 * 
	 * @param event
	 *            NEvent to change into a VEvent.
	 * @return VEvent representation of given NEvent.
	 */
	private static VEvent createEvent(NEvent event) {
		// Getting the starting and ending dates for event.
		DateTime start = new DateTime(event.getStartDate().getTime());
		DateTime end = new DateTime(event.getEndDate().getTime());

		// Make summary, location and description.
		Location loc = new Location(event.getLocation());
		Description desc = new Description(event.getDescription());

		// Make VEvent and add properties to it.
		VEvent vev = new VEvent(start, end, event.getName());
		vev.getProperties().add(loc);
		vev.getProperties().add(desc);

		return vev;
	}

	/**
	 * Method for making a VEvent object out of NEvent assignment.
	 * 
	 * @param assignment
	 *            NEvent to change into a VEvent.
	 * @return VEvent representation of given NEvent.
	 */
	private static VEvent createAssignment(NEvent assignment) {
		// Making the start and end dates for assignment.
		java.util.Calendar endc = assignment.getEndDate();
		java.util.Calendar startc = endc;
		startc.add(java.util.Calendar.HOUR, -1);
		DateTime start = new DateTime(startc.getTime());
		DateTime end = new DateTime(endc.getTime());

		// Make summary and description.
		String summary = assignment.getName() + " palautus";
		Description desc = new Description(assignment.getDescription());

		// Make VEvent and add properties to it.
		VEvent vas = new VEvent(start, end, summary);
		vas.getProperties().add(desc);

		return vas;
	}

	/**
	 * Parses current period from Aalto University's website.
	 * 
	 * @return Current period. (Roman numeral 1-4).
	 */
	private static String getCurrentPeriod() {
		try {
			// Try to connect to Aalto-webpage.
			URL toconnect = new URL(
					"https://into.aalto.fi/display/fimastersci/"
							+ "Opetusperiodit+ja+arviointijaksot");
			URLConnection yc = toconnect.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					yc.getInputStream()));

			// Create formatter and current time.
			SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
			java.util.Calendar currentdate = java.util.Calendar.getInstance();
			currentdate.setTimeZone(java.util.TimeZone
					.getTimeZone("Europe/Helsinki"));

			// Create calendar for period.
			java.util.Calendar perioddate = (java.util.Calendar) currentdate
					.clone();

			// Create needed variables.
			String inputLine;
			boolean readnext = false;
			int period = 0;

			// Start reading webpage.
			while ((inputLine = in.readLine()) != null) {
				// In case next line should be read (it contains information
				// about period).
				if (readnext) {
					// This is to keep track of which period is the current one
					// being read.
					period++;

					// Get the part of line that includes period's ending date.
					int start = inputLine.indexOf("&#8211;") + 10;
					int end = inputLine.indexOf("</font>");
					String date = inputLine.substring(start, end);
					try {
						// Parse period's ending date into a calendar.
						perioddate.setTime(formatter.parse(date));

						// Compare current date with parsed period's ending
						// date.
						if (compare(currentdate, perioddate)) {

							// Return current period.
							if (period == 2) {
								return "II";
							} else if (period == 3) {
								return "III";
							} else if (period == 4) {
								return "IV";
							} else {
								return "I";
							}
						}
					} catch (ParseException e) {
						// This shouldn't happen.
					}
				} // end of if-clause.

				// In case next line contains dates for period.
				if (inputLine.contains(" periodi")) {
					readnext = true;
				} else {
					readnext = false;
				}
			} // end of while-loop;

			// Close connection.
			in.close();
		} catch (IOException e) {
			// This should never happen.
		}

		// Return period 1.
		return "I";
	}

	/**
	 * Compares current date and given parsed date and concludes if given
	 * enddate is of current period.
	 * 
	 * @param current
	 *            Current date.
	 * @param parsed
	 *            Parsed period's ending date.
	 * @return <code>true</code>, if parsed period is the current one.
	 */
	private static boolean compare(java.util.Calendar current,
			java.util.Calendar parsed) {
		// Calculate the difference in years.
		int yeardif = current.get(java.util.Calendar.YEAR)
				- parsed.get(java.util.Calendar.YEAR);

		// If period is in the past or far future (more than 1 year away),
		// false.
		if (yeardif > 0 || yeardif < -1) {
			return false;
		}

		// Calculate the difference in months.
		int monthdif = current.get(java.util.Calendar.MONTH)
				- parsed.get(java.util.Calendar.MONTH);

		// If period is in the next year and monthdifference is not big enough
		// (current is december and period is january for example), false.
		if (yeardif == -1 && monthdif < 9) {
			return false;
		}

		// If period and current year are the same, but period is in the past
		// (months), false.
		if (yeardif == 0 && monthdif > 0) {
			return false;
		}

		// Calculate the difference in days.
		int daydif = current.get(java.util.Calendar.DAY_OF_MONTH)
				- parsed.get(java.util.Calendar.DAY_OF_MONTH);

		// If current year and month are the same, but period's ending day is in
		// the past, false.
		if (yeardif == 0 && monthdif == 0 && daydif > 0) {
			return false;
		}

		// All checks passed, given period is current one.
		return true;
	}
}
