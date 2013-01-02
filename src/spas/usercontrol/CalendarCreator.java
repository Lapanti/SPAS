package spas.usercontrol;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
import net.fortuna.ical4j.model.property.DtStamp;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.RRule;
import net.fortuna.ical4j.model.property.Transp;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.UidGenerator;
import spas.nelements.NCourse;
import spas.nelements.NEvent;
import spas.nreading.NReader;

/**
 * Class for creating an iCalendar representation of user's courses' events.
 * 
 * @author Lauri Lavanti
 * @version 0.2
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

		// Create a timestamp and transparency.
		DtStamp dtstamp = new DtStamp();
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

			// Get user's courses and loop through them all.
			List<NCourse> courses = handler.getCourses();
			for (NCourse c : courses) {
				if (handler.getState(c.getId()) == 1) {
					
					// Get group for course.
					String group = handler.getGroup(c.getId());

					// Get events for course and merge them all to one list.
					List<NEvent> events = nreader.getCourseLectures(c);
					events.addAll(nreader.getCourseExercises(c));
					events.addAll(nreader.getCourseAssignments(c));
					events.addAll(nreader.getCourseEvents(c));
					
					// Loop through all the events.
					loop: for (NEvent event : events) {
						// Create the correct VEvent based on it's type.
						VEvent vevent;
						type: switch (event.type) {
						case LECTURE:
							vevent = createLecture(event);
							break type;
						case EXERCISE:
							// In case of a chosen group, ignore other
							// exercises.
							if (!group.equalsIgnoreCase(event.getGroup())
									&& !group.equals("")) {
								continue loop;
							}
							vevent = createExercise(event);
							break type;
						case ASSIGNMENT:
							vevent = createAssignment(event);
							break type;
						case OTHER:
							vevent = createEvent(event);
							break type;
						default:
							continue loop;
						}
						// Add basic properties to VEvent.
						vevent.getProperties().add(tz);
						vevent.getProperties().add(dtstamp);
						vevent.getProperties().add(transp);
						vevent.getProperties().add(ug.generateUid());

						// Add VEvent to calendar.
						calendar.getComponents().add(vevent);
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
	 * @param lecture NEvent to change into a VEvent.
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
	 * @param exercise NEvent to change into a VEvent.
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
	 * @param event NEvent to change into a VEvent.
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
	 * @param assignment NEvent to change into a VEvent.
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
}
