package com.NativeTech.rehla.Network;


import com.NativeTech.rehla.model.data.dto.Models.AcceptOrReject.AcceptOrRejectResponse;
import com.NativeTech.rehla.model.data.dto.Models.AddCar.AddCarRequestModel;
import com.NativeTech.rehla.model.data.dto.Models.AddCar.AddCarResponseModelFinal;
import com.NativeTech.rehla.model.data.dto.Models.AddCarPhotoModels.AddCarPhotoRequest;
import com.NativeTech.rehla.model.data.dto.Models.AddCarPhotoModels.AddCarResponse;
import com.NativeTech.rehla.model.data.dto.Models.AddContactUs.AddContactUsRequestModel;
import com.NativeTech.rehla.model.data.dto.Models.AddToken.AddTokenRequest;
import com.NativeTech.rehla.model.data.dto.Models.AddToken.ErrorsModelResponse;
import com.NativeTech.rehla.model.data.dto.Models.AddTripModels.AddTripRequest;
import com.NativeTech.rehla.model.data.dto.Models.AddTripModels.AddTripResponseModel;
import com.NativeTech.rehla.model.data.dto.Models.CarsModels.getAllCarsResponse;
import com.NativeTech.rehla.model.data.dto.Models.ChangePassword.ChangePasswordRequest;
import com.NativeTech.rehla.model.data.dto.Models.ChangePassword.ChangePasswordResponseModel;
import com.NativeTech.rehla.model.data.dto.Models.ChatCount.ChatResponseModel;
import com.NativeTech.rehla.model.data.dto.Models.Chats.ChatDetailsResponse;
import com.NativeTech.rehla.model.data.dto.Models.Chats.ChatUserResponse;
import com.NativeTech.rehla.model.data.dto.Models.Driver.getCurrentTripResponse;
import com.NativeTech.rehla.model.data.dto.Models.Driver.updateTripStatusResponse;
import com.NativeTech.rehla.model.data.dto.Models.EditPreferences.EditPreferencesRequest;
import com.NativeTech.rehla.model.data.dto.Models.ErrorsModel;
import com.NativeTech.rehla.model.data.dto.Models.ErrorsModelMessageOnly;
import com.NativeTech.rehla.model.data.dto.Models.ForgetPassword.ForgetPasswordResponseModel;
import com.NativeTech.rehla.model.data.dto.Models.GetAllTransactions.GetAllTransactionsResponse;
import com.NativeTech.rehla.model.data.dto.Models.GetDriverProfile.DriverProfileResponseModel;
import com.NativeTech.rehla.model.data.dto.Models.GetFAQs.GetFAQsResponseModel;
import com.NativeTech.rehla.model.data.dto.Models.GetRateSummary.GetAllRatesResponse;
import com.NativeTech.rehla.model.data.dto.Models.GetRateSummary.getRateSummaryResponse;
import com.NativeTech.rehla.model.data.dto.Models.LoginModel.LoginResponseModel;
import com.NativeTech.rehla.model.data.dto.Models.MetaDataModels.MetaDataResponseModel;
import com.NativeTech.rehla.model.data.dto.Models.Notifications.NotificationResponse;
import com.NativeTech.rehla.model.data.dto.Models.Passenger.getPassengerCurrentRidesResponse;
import com.NativeTech.rehla.model.data.dto.Models.RateDriver.RateDriverRequest;
import com.NativeTech.rehla.model.data.dto.Models.RateDriver.RateDriverResponse;
import com.NativeTech.rehla.model.data.dto.Models.Register.RegisterRequestModel;
import com.NativeTech.rehla.model.data.dto.Models.SearchResult.SearchResponse;
import com.NativeTech.rehla.model.data.dto.Models.SendToBank.sendToBankRequest;
import com.NativeTech.rehla.model.data.dto.Models.TripDetails.getTripDetailsResponse;
import com.NativeTech.rehla.model.data.dto.Models.UpdateImage.UploadProfileImageRequest;
import com.NativeTech.rehla.model.data.dto.Models.UploadIdentityCard.UploadIdentityCardRequest;
import com.NativeTech.rehla.model.data.dto.Models.WaitingTrips.addWaitingTripsRequest;
import com.NativeTech.rehla.model.data.dto.Models.WaitingTrips.addWaitingTripsResponse;
import com.NativeTech.rehla.model.data.dto.Models.WaitingTrips.getAllWaitingTripsResponse;
import com.NativeTech.rehla.model.data.dto.Models.addReservation.addReservationRequest;
import com.NativeTech.rehla.model.data.dto.Models.checkDriverStartedTripResponse;
import com.NativeTech.rehla.model.data.dto.Models.getCityModels.SearchResponseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface RetrofitInterface {

    @GET("Users/Login")
    Observable<LoginResponseModel> login(@Query("Email") String Email,@Query("Password") String Password );

    @POST("Users/Register")
    Observable<LoginResponseModel> register(@Body RegisterRequestModel registerRequestModel);

    @GET("MetaData/GetAll")
    Observable<MetaDataResponseModel> metaData();

    @GET("GetFAQs")
    Observable<GetFAQsResponseModel> GetFAQs();

    @GET("Users/Verify")
    Observable<LoginResponseModel> verify(@Query("PhoneNumber") String PhoneNumber,@Query("Vcode") String Vcode);

    @POST("changePassword")
    Observable<ChangePasswordResponseModel> changePassword(@Body ChangePasswordRequest changePasswordRequest);

    @POST("updateProfile")
    Observable<LoginResponseModel> updateProfile(@Body RegisterRequestModel updateProfileDataRequestModel);

    @GET("Users/GetUserProfile")
    Observable<LoginResponseModel> GetUserProfile();

    @POST("changeUserPhoto")
    Observable<LoginResponseModel> UploadImage(@Body UploadProfileImageRequest uploadProfileImageRequest);

    @POST("uploadIdentityImage")
    Observable<LoginResponseModel> UploadIdentityCard(@Body UploadIdentityCardRequest uploadProfileImageRequest);

    @POST("EditPreferences")
    Observable<LoginResponseModel> EditPreferences(@Body EditPreferencesRequest editPreferencesRequest);

    @GET("getAllCars")
    Observable<getAllCarsResponse> getAllCars();

    @POST("users/AddContactUs")
    Observable<ChangePasswordResponseModel> AddContactUs(@Body AddContactUsRequestModel addContactUsRequestModel);

    @GET("forgetPassword")
    Observable<ForgetPasswordResponseModel> forgetPassword(@Query("PhoneNumber") String PhoneNumber);

    @POST("AddCarPhoto")
    Observable<AddCarResponse> AddCarPhoto(@Body AddCarPhotoRequest addCarPhotoRequest);

    @POST("AddCar")
    Observable<AddCarResponseModelFinal> AddCar(@Body AddCarRequestModel addCarRequestModel);

    @GET("deleteCar")
    Observable<updateTripStatusResponse> deleteCar(@Query("carId") String carId);

   @GET("getDriverCurrentTrip")
    Observable<getCurrentTripResponse> getDriverCurrentTrip(@Query("Page") String carId);

   @GET("getDriverPreviousTrip")
    Observable<getCurrentTripResponse> getDriverPreviousTrip(@Query("Page") String carId);

   @GET("updateTripStatus")
    Observable<updateTripStatusResponse> updateTripStatus(@Query("tripId") String tripId, @Query("statusId") String statusId);

   @GET("getTripDetails")
    Observable<getTripDetailsResponse> getTripDetails(@Query("tripId") String tripId);


   @GET("findTrip")
    Observable<SearchResponse> findTrip(
             @Query("sourceCity") String sourceCity
           , @Query("destinationCity") String destinationCity
           , @Query("date") String date
           , @Query("Page") String Page);

    @POST("AddTrip")
    Observable<AddTripResponseModel> AddTrip(@Body AddTripRequest addTripRequest);

    @GET("getPassengerCurrentRides")
    Observable<getPassengerCurrentRidesResponse> getPassengerCurrentRides(@Query("Page") String carId);

    @GET("getPassengerPreviousRides")
    Observable<getPassengerCurrentRidesResponse> getPassengerPreviousRides(@Query("Page") String carId);

    @POST("addReservation")
    Observable<updateTripStatusResponse> addReservation(@Body addReservationRequest addReservationRequest);

    @POST("users/AddToken")
    Observable<ErrorsModelResponse> AddToken(@Body AddTokenRequest addTokenRequest);

    @GET("getReservationsBYTripID")
    Observable<AcceptOrRejectResponse> getReservationsBYTripID(@Query("tripId") String tripId);

    @GET("GetAllNotification")
    Observable<NotificationResponse> GetAllNotification(@Query("Page") String Page);

    @GET("AcceptOrRejectReservation")
    Observable<updateTripStatusResponse> AcceptOrRejectReservation(@Query("reservationID") String reservationID
            , @Query("isAccept") String Page);

    @GET("CancelReservation")
    Observable<updateTripStatusResponse> CancelReservation(@Query("reservationID") String reservationID);

    @GET("GetDriverProfile")
    Observable<DriverProfileResponseModel> GetDriverProfile(@Query("Driverid") String Driverid);

    @POST("RateADriver")
    Observable<RateDriverResponse> RateADriver(@Body RateDriverRequest rateDriverRequest);

    @POST("RateAPassenger")
    Observable<RateDriverResponse> RateAPassenger(@Body RateDriverRequest rateDriverRequest);

    @GET("getRateSummary")
    Observable<getRateSummaryResponse> getRateSummary();

    @GET("getAllRates")
    Observable<GetAllRatesResponse> getAllRates(@Query("Page") String Page);

    @GET("GetAllTransactions")
    Observable<GetAllTransactionsResponse> GetAllTransactions(@Query("Page") String Page);

    @GET("AddTransaction")
    Observable<ErrorsModel> AddTransaction(@Query("CardNumber") String CardNumber);

    @POST("AddWithdrawalRequest")
    Observable<ErrorsModel> SendToBank(@Body sendToBankRequest sendToBankRequest);

    @GET("getAllRatesByUserId")
    Observable<GetAllRatesResponse> getAllRatesByUserId(@Query("UserId") String UserId,@Query("Page") String Page);


    @GET("getAllUserMessagesWithOthers")
    Observable<ChatUserResponse> getAllUserMessagesWithOthers(@Query("Page") String Page);

    @GET("RequestEmailVerification")
    Observable<ErrorsModel> RequestEmailVerification();

    @GET("VerifyEmail")
    Observable<ErrorsModel> VerifyEmail(@Query("vcode") String vcode);

    @GET("RequestUserPhoneVerification")
    Observable<ErrorsModelMessageOnly> RequestUserPhoneVerification(@Query("phone") String phone);

    @POST("AddWaitingTrip")
    Observable<addWaitingTripsResponse> AddWaitingTrip(@Body addWaitingTripsRequest addWaitingTripsRequest);

    @GET("getAllUserWaitingTrips")
    Observable<getAllWaitingTripsResponse> getAllUserWaitingTrips(@Query("Page") String Page);

    @GET("DeleteWaitingTrip")
    Observable<addWaitingTripsResponse> DeleteWaitingTrip(@Query("TripId") String TripId);

    @GET("geocode/json")
    Observable<SearchResponseModel> getCityAR(@Query("latlng") String latlng,
                                                         @Query("language") String language,
                                                         @Query("key") String key);
   @GET("addCurrentLocation")
    Observable<addWaitingTripsResponse> addCurrentLocation(
             @Query("tripId") String tripId
           ,@Query("latitude") String latitude
           ,@Query("longitude") String longitude
           ,@Query("dateTime") String dateTime);


   @GET("checkDriverStartedTrip")
    Observable<checkDriverStartedTripResponse> checkDriverStartedTrip();

   @GET("getUnseenMessagesCount")
    Observable<ChatResponseModel> getUnseenMessagesCount();

   @GET("seeAllMessages")
    Observable<ChatResponseModel> seeAllMessages(@Query("partnerId") String partnerId);

//    @GET("getChatMessages")
//    Observable<ChatDetailsResponse> getChatMessages(@Query("Page") int Page, @Query("partnerId") String partnerId);

    @GET("getChatMessages")
    Call<ChatDetailsResponse> getChatMessages(@Query("Page") int Page, @Query("partnerId") String partnerId);

}