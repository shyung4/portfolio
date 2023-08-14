/**
 * 
 */
 
//  $('.js-click-modal').click(function(){
//   $('.container').addClass('modal-open');
// });

// $('.js-close-modal').click(function(){
//   $('.container').removeClass('modal-open');
// });




function search(target){
  // elementary, middle, high
    // var searchType = $('input[name="mbnm"]:checked').val(); 

    $.ajax({
        type: 'GET',
        dataType: 'JSON',
        url: `../json/output.json`, 
        error: function(err){
            console.log(err);
        },
        success: function(data){
            var checkWord = $("#searchInput").val();
            var searchedResult = $("#searchedMemList");
            console.log(checkWord);
            console.log(data);
            data =JSON.stringify(data)
            data = JSON.parse(data);
            searchedResult.empty();
            data.forEach((mbnm)=>{
                if(mbnm['mbnm'].includes(checkWord)){
                    searchedResult.append(`<span style="cursor: pointer;""> ${mbnm['mbnm']} </span> <br/>`);                
                }
            })
        }
    })
} 