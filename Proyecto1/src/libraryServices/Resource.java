package libraryServices;

public class Resource
{
    private String summary;

    public Resource(String pSummary)
    {
        setSummary(pSummary);
    }

    public String getSummary() {
        return summary;
    }

    private void setSummary(String summary) {
        this.summary = summary;
    }
}
