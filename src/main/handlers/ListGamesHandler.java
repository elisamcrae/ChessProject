package handlers;

import chess.ChessGameE;
import com.google.gson.Gson;
import requests.RegisterRequest;
import responses.RegisterResponse;
import services.ListGamesService;
import services.RegisterService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.ArrayList;

public class ListGamesHandler implements Route {
    //IS THIS RIGHT???
    @Override
    public Object handle(Request request, Response response) throws Exception {
        //RegisterRequest req = (RegisterRequest)new Gson().fromJson(request.body(), RegisterRequest.class);

        ListGamesService service = new ListGamesService();
        ArrayList<ChessGameE> gameList = service.listGames();
        //RegisterResponse result = service.register(req);

        return new Gson().toJson(gameList);
    }
}
