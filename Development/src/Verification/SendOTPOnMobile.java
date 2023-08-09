package Verification;

import java.io.IOException;

import java.net.*;
import java.sql.*;
import java.util.Random;

import database.*;

public class SendOTPOnMobile 
{
	public static String generateOTP()
	{
		String characters = "123456789";
		Random random = new Random();
		char otp[] = new char[5];

		for(int i = 0; i < 5; i++) 
		{
        	otp[i] = characters.charAt(random.nextInt(characters.length()));
      	}
		String OTP = new String(otp);
		return OTP;
	}

	public static boolean is_OTP_unique(String OTP) throws SQLException
	{
		String query = "select * from otp where otp=?;";
		String queryConditionVars[] = new String[1];
		queryConditionVars[0] = OTP;
		
		ResultSet resultset = ConnectionDB.getResult(query, queryConditionVars);
		if(resultset.next() == false)
		{
			return true;
		}
		return false;
	}

	public static int sendOTP(String mobNum, String mssg) throws SQLException, IOException
	{	
		String OTP = generateOTP();
		while(is_OTP_unique(OTP) == false)
		{
			OTP = generateOTP();
		}
		mssg += OTP;
		
		
		// 8BxRPsXkcrazntuSIE9piQHYO2bUhT6lFgJV1D5CL7mGN0foAw0JbokNMWeL2TqFYtzuGlARH3XvndxE
		// VihNFREJepgHo5MvSUCqWy0798GKsIAdzYBkDlarjZcPT1Q2L32b4BSmZD5FdVUIXK7cnOfvh1sHeEYw
		String apiKey = "VihNFREJepgHo5MvSUCqWy0798GKsIAdzYBkDlarjZcPT1Q2L32b4BSmZD5FdVUIXK7cnOfvh1sHeEYw";
		String url = "https://www.fast2sms.com/dev/bulkV2?authorization=" + apiKey + "&message=" + mssg + "&language=english&route=q&numbers=" + mobNum;

		try 
		{
			URL urlObj = new URL(url);
			HttpURLConnection connection = (HttpURLConnection)urlObj.openConnection();
			
			connection.setRequestMethod("GET");
			connection.setRequestProperty("cache-control", "no-cache");

			int respCode = connection.getResponseCode();
			System.out.print(respCode);
			if(respCode == 200)
			{
				ConnectionDB.executeQuery("insert into otp values(" + OTP + ", " + mobNum + ", null);");
			}
			
			return respCode;
		} 
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		}
		return -1;
	}
}


