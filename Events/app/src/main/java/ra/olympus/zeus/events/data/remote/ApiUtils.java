package ra.olympus.zeus.events.data.remote;

public class ApiUtils {
    private ApiUtils() {}

    public static final String BASE_URL = "https://22b45908-b3d8-4982-bf37-588b2798bdbf.mock.pstmn.io";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
