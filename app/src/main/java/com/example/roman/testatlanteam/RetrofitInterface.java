package com.example.roman.testatlanteam;

import com.example.roman.testatlanteam.model.Comments;
import com.example.roman.testatlanteam.model.Posts;
import com.example.roman.testatlanteam.model.Todos;
import com.example.roman.testatlanteam.model.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;



public interface RetrofitInterface {
    @GET("/posts/{number}")
    Call<Posts> getPosts(@Path("number") String user);

    @GET("/comments/{number}")
    Call<Comments> getComments(@Path("number") String user);

    @GET("/todos/{todos}")
    Call<Todos> getTodos(@Path("todos") String user);

    @GET("/users")
    Call<List<Users>> getUsers();
}
