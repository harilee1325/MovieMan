package com.harilee.movieman;

import io.reactivex.Observable;

import com.harilee.movieman.Details.DetailsResponse;
import com.harilee.movieman.Details.ReviewResponse;
import com.harilee.movieman.DetailsTv.TvDetails;
import com.harilee.movieman.ForgotPassword.NewPasswordResponse;
import com.harilee.movieman.ForgotPassword.VerifyMailResponse;
import com.harilee.movieman.Login.LoginResponse;
import com.harilee.movieman.MovieList.MovieResponse;
import com.harilee.movieman.Seasons.SeasonResponse;
import com.harilee.movieman.SignUp.SignupResponse;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface NetworkInterface {

    @GET("/login/{username}/{password}")
    Observable<LoginResponse> loginUser(@Path("username") String username, @Path("password") String password);

    @FormUrlEncoded
    @POST("/sign-up")
    Observable<SignupResponse> signupUser(@Field("username") String username, @Field("password") String password
                                          ,@Field("email") String email);
    @GET("/forgot-password/{user_email}")
    Observable<VerifyMailResponse> verifyMailidOfUser(@Path("user_email") String email);

    @FormUrlEncoded
    @POST("/forgot-change-password")
    Observable<NewPasswordResponse> setNewPassword(@Field("email") String email, @Field("new_password") String newPassword);

    // movie list
    @GET("discover/movie?language=en&sort_by=release_date.desc")
    Observable<MovieResponse> newMovies(@Query("api_key") String api_key,@Query("release_date.lte") String maxReleaseDate
            , @Query("vote_count.gte") int maxVote, @Query("page") int page);

    // movie details
    @GET("movie/{movie_id}?language=en-US")
    Observable<DetailsResponse> movieDetails(@Path("movie_id")String movieId,@Query("api_key") String tmdbApiKey);

    // movie review
    @GET("movie/{movie_id}/reviews?language=en-US&page=1")
    Observable<ReviewResponse> reviewDetails(@Path("movie_id") String movieId,@Query("api_key") String tmdbApiKey);

    //list of tv show
    @GET("discover/tv?language=en&sort_by=popularity.desc")
    Observable<com.harilee.movieman.MovieList.TvResponse> newTvshow(@Query("api_key") String tmdbApiKey, @Query("air_date.gte") String maxReleaseDate
            , @Query("vote_count.gte") int i, @Query("page") int pageNumber);

    // tv details
    @GET("tv/{tv_id}?language=en-US")
    Observable<TvDetails> tvDetails(@Path("tv_id") String movieId, @Query("api_key") String tmdbApiKey);

    // tv reviews
    @GET("tv/{tv_id}/reviews?language=en-US&page=1")
    Observable<ReviewResponse> reviewDetailsTv(@Path("tv_id") String movieId,@Query("api_key") String tmdbApiKey);

    // episode list
    @GET("tv/{tv_id}/season/{season_number}?language=en-US")
    Observable<SeasonResponse> getEpisodes(@Path("tv_id") String sid, @Path("season_number") String seasonNumber,@Query("api_key") String tmdbApiKey);
}




