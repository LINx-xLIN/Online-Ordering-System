$(function(){
	$(".address").hover(function(){
		$(this).addClass("address-hover");	
	},function(){
		$(this).removeClass("address-hover");	
	});
})

$(function(){
	$(".addr-item .test .name").click(function(){


		$(".addr-item .test .name").removeClass("selected");
		$(this).toggleClass("selected");
		var provinceOrder = $(this.parentNode).find("span.provinceOrder").text();
		var cityOrder = $(this.parentNode).find("span.cityOrder").text();
		var regionOrder = $(this.parentNode).find("span.regionOrder").text();
		var detailAddressOrder = $(this.parentNode).find("span.detailAddressOrder").text();
		var phoneNumberOrder = $(this.parentNode).find("span.phoneNumberOrder").text();
		var nameOrder = $(this.parentNode).find(".nameOrder").text();

		$("#addressOrder").text("寄送至:"+provinceOrder+cityOrder+regionOrder+detailAddressOrder+" 收货人:"+nameOrder+"   "+phoneNumberOrder);

		$("#orderForm").empty();

		$("#orderForm").append(


			"           <input type=\"hidden\" name=\"orderShipping.receiverName\" value=\"" +
			nameOrder +
			"\"/>\n" +
			"               <input type=\"hidden\" name=\"orderShipping.receiverMobile\" value=\"" +
			phoneNumberOrder +
			"\"/>\n" +
			"               <input type=\"hidden\" name=\"orderShipping.receiverState\" value=\"" +
			provinceOrder +
			"\"/>\n" +
			"               <input type=\"hidden\" name=\"orderShipping.receiverCity\" value=\"" +
			cityOrder +
			"\"/>\n" +
			"               <input type=\"hidden\" name=\"orderShipping.receiverDistrict\" value=\"" +
			regionOrder +
			"\"/>\n" +
			"               <input type=\"hidden\" name=\"orderShipping.receiverAddress\" value=\"" +
			detailAddressOrder +
			"\"/>"
		)


	});





	$(".payType li").click(function(){

		 $(this).toggleClass("selected").siblings().removeClass("selected");

	});
})
