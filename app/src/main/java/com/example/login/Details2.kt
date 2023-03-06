package com.example.login

data class Details2(
val id:Int,
val cast:List<cast>,
val crew:List<crew>,
val page:Int,val results:List<Result>,val total_pages:Int
):java.io.Serializable {
}


data class crew(
    val original_name:String,
    val profile_path:String,
    val job:String
){

}