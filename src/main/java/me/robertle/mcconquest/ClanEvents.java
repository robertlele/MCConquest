package me.robertle.mcconquest;

import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class ClanEvents {

    public static HashMap<Integer, Event> eventTimes = new HashMap<>();
    public static Event currentEvent;
    public static boolean eventActive;
    public static boolean eventToggle = true;

    //Lucky Pit


    public static void runEventTimers() {
        new BukkitRunnable() {
            public void run() {
                if (!eventToggle) return;
                for (int eventTime : eventTimes.keySet()) {
                    if (DateUtil.getHour() == eventTime && currentEvent != eventTimes.get(eventTime)) {
                        stopCurretEvent();
                        currentEvent = eventTimes.get(DateUtil.getHour());
                        startEvent(eventTimes.get(DateUtil.getHour()));
                        int time = eventTime;
                        Event event = eventTimes.get(time);
                        eventTimes.remove(time);
                        if (time - 1 < 0) time = (time - 1) + 24;
                        else time = time - 1;
                        eventTimes.put(time, event);
                    }
                }
            }
        }.runTaskTimer(Core.instance, 0L, 1200);
    }

    public static void startEvent(Event event) {
        if (event == Event.LUCKY_PIT) {
            eventActive = true;
            currentEvent = Event.LUCKY_PIT;
            Core.shoutCenter("Test");
        } else if (event == Event.KOTH) {

        } else if (event == Event.BEAT_DOWN) {

        } else if (event == Event.TREASURE_HUNT) {

        }
    }

    public static void stopCurretEvent() {
        eventActive = false;
    }

    public static Event getNextEvent() {
        int timeCheck = DateUtil.getHour();
        for (int i = 1; i < 24; i++) {
            timeCheck += i;
            if (timeCheck > 23) {
                if (eventTimes.containsKey(timeCheck - 24)) return eventTimes.get(timeCheck - 24);
            } else {
                if (eventTimes.containsKey(timeCheck)) return eventTimes.get(timeCheck);
            }
        }
        return null;
    }

    public static int getNextEventInHours(Event event) {
        int timeCheck = DateUtil.getHour();
        for (int i = 1; i < 24; i++) {
            timeCheck += i;
            if (timeCheck > 23) {
                if (eventTimes.containsKey(timeCheck - 24)) {
                    if (eventTimes.get(timeCheck - 24) == event)
                        return (timeCheck) - DateUtil.getHour();
                }
            } else {
                if (eventTimes.containsKey(timeCheck)) {
                    if (eventTimes.get(timeCheck) == event)
                        return (timeCheck) - DateUtil.getHour();
                }
            }
        }
        return -1;
    }
}
