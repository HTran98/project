/**
 * 
 */
$(document).ready(function(){
	$('.table .edit').on('click',function(event){
		event.preventDefault();
		var href = $(this).attr('href');
		$.get(href,function(category,status){
			$('.modal #name').val(category.name);
			console.log(category.name);
		});
		$('.modal #exampleModal').modal();
	});
}
		);