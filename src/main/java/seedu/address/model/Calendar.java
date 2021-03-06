package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.entity.CalendarItem;
import seedu.address.model.entity.Module;
import seedu.address.model.entity.UniqueCalendarItemList;
import seedu.address.model.entity.UniqueModuleList;

/**
 * Wraps all data at the calendar level
 * Duplicates are not allowed (by .isSameModule comparison)
 */
public class Calendar implements ReadOnlyCalendar {

    private final UniqueCalendarItemList calendarItems;
    private final UniqueModuleList modules;

    /*
     *  The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     *  between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     *  Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *  among constructors.
     */
    {
        calendarItems = new UniqueCalendarItemList();
        modules = new UniqueModuleList();
    }

    public Calendar() {
    }

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public Calendar(ReadOnlyCalendar toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setModules(List<Module> modules) {
        this.modules.setModules(modules);
    }

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasModule(Module module) {
        requireNonNull(module);
        return modules.contains(module);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addModule(Module module) {
        modules.add(module);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setModule(Module target, Module editedModule) {
        requireNonNull(editedModule);

        modules.setModule(target, editedModule);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeModule(Module key) {
        modules.remove(key);
    }

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setCalendarItems(List<CalendarItem> calendarItems) {
        this.calendarItems.setCalendarItems(calendarItems);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setCalendarItems(CalendarItem target, CalendarItem editedCalendarItem) {
        requireNonNull(editedCalendarItem);
        calendarItems.setCalendarItem(target, editedCalendarItem);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyCalendar newData) {
        requireNonNull(newData);

        setCalendarItems(newData.getCalendarItemList());
        setModules(newData.getModuleList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasCalendarItem(CalendarItem calendarItem) {
        requireNonNull(calendarItem);
        // System.out.println("Calendar item !!!!!!!!!!!!!!!"+ calendarItem);
        return calendarItems.contains(calendarItem);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addCalendarItem(CalendarItem calendarItem) {
        calendarItems.add(calendarItem);
    }


    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeCalendarItem(CalendarItem key) {
        calendarItems.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return calendarItems.asUnmodifiableObservableList().size() + " calendar items";
        // TODO: refine later
    }

    @Override
    public ObservableList<CalendarItem> getCalendarItemList() {
        return calendarItems.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Module> getModuleList() {
        return modules.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Calendar // instanceof handles nulls
                && calendarItems.equals(((Calendar) other).calendarItems));
    }

    @Override
    public int hashCode() {
        return calendarItems.hashCode();
    }
}
