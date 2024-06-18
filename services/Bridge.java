import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.rmi.RemoteException;
import java.time.Duration;

public class Bridge implements ServiceBridge{

    @Override
    public String getIncidents() throws RemoteException, InterruptedException, IOException {
        HttpClient client = //HttpClient.newHttpClient();
        HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .followRedirects(HttpClient.Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(20))
            .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://carto.g-ny.org/data/cifs/cifs_waze_v2.json"))
                .timeout(Duration.ofMinutes(2))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> r = client.send(request, HttpResponse.BodyHandlers.ofString());

        String response = r.body();

        return response;
    }

    @Override
    public String getSup() throws RemoteException, InterruptedException, IOException {
        HttpClient client = //HttpClient.newHttpClient();
        HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .followRedirects(HttpClient.Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(20))
            .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://data.enseignementsup-recherche.gouv.fr/api/explore/v2.1/catalog/datasets/fr-esr-implantations_etablissements_d_enseignement_superieur_publics/records?limit=50&refine=localisation%3A%22Alsace%20-%20Champagne-Ardenne%20-%20Lorraine%3ENancy-Metz%3EMeurthe-et-Moselle%3ENancy%22"))
                .timeout(Duration.ofMinutes(2))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> r = client.send(request, HttpResponse.BodyHandlers.ofString());
        String response = r.body();

        // On stock ici les données qui nous intéressent
        JSONObject responseJson = new JSONObject("""
                {
                    "etablissements": []
                }
                """);

        // On parse en json pour parcourir les données et récupérer ce qui nous intéresse
        JSONObject obj = new JSONObject(response);
        JSONArray etablissements = obj.getJSONArray("results");
        for (int i = 0; i < etablissements.length(); i++) {
            JSONObject newEtablissement = new JSONObject();
            JSONObject etablissement = etablissements.getJSONObject(i);
            newEtablissement.put("latitude", etablissement.getJSONObject("coordonnees").getDouble("lat"));
            newEtablissement.put("longitude", etablissement.getJSONObject("coordonnees").getDouble("lon"));
            newEtablissement.put("type", etablissement.getString("type_d_etablissement"));
            newEtablissement.put("siege", etablissement.getString("siege_lib"));
            newEtablissement.put("code_postal", etablissement.getString("com_code"));
            newEtablissement.put("adresse", etablissement.getString("adresse_uai"));

            responseJson.append("etablissements", newEtablissement);
        }

        return responseJson.toString();
    }

    public static void main(String[] args) {
        Bridge bridge = new Bridge();
        try {
            bridge.getSup();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}