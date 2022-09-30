package gameoflife.view;

/**
 * Used for localization of the used texts.
 * Currently only english and hard coded values
 */
public class Locale {
    private String populate;
    private String startPainting;
    private String continueSimulation;
    private String startRecording;
    private String stopRecording;
    private String setFPS;
    private String current;

    /**
     * Ininitalizes new <code>Locale</code> object.
     * Sets the text for each string.
     */
    public Locale() {
        populate = "Populate";
        startPainting ="Start painting";
        continueSimulation = "continue simulation";
        startRecording = "Start recording";
        stopRecording = "Stop recording";
        setFPS = "Set FPS";
        current = "currently";
    }


    /**
     * Returns the set text for "populate"
     *
     * @return string for "populate"
     */
    public String getPopulate() {
        return populate;
    }

    /**
     * Returns the set text for "start painting"
     * @return string for "start painting"
     */
    public String getStartPainting() {
        return startPainting;
    }

    /**
     * Returns the set text for "continue simulation"
     *
     * @return string for "continue simulation"
     */
    public String getContinueSimulation() {
        return continueSimulation;
    }

    /**
     * Returns the set text for "start recording"
     *
     * @return string for "start recording"
     */
    public String getStartRecording() {
        return startRecording;
    }

    /**
     * Returns the set text for "stop recording"
     *
     * @return string for "stop recording"
     */
    public String getStopRecording() {
        return stopRecording;
    }

    /**
     * Returns the set text for "set FPS: "
     *
     * @return string for "set FPS"
     */
    public String getSetFPS() {
        return setFPS;
    }


    /**
     * Returns the set text for "current"
     *
     * @return string for "current"
     */
    public String getCurrent() {
        return current;
    }
}
