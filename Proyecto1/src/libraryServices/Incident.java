package libraryServices;

public class Incident
{
    private String summary;

    public Incident(String pSummary)
    {
        this.summary = pSummary;
    }

    public String getSummary() {
        return summary;
    }

    private void setSummary(String summary) {
        this.summary = summary;
    }
}
