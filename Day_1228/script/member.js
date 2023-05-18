window.onload = memlist;
function memlist(){
    let list = [{"이름":"홍길동", "등급":"일반회원", "가입일":"20/01/04"} //0번방
               ,{"이름":"손오공", "등급":"실버회원", "가입일":"22/05/05"} //1번방
               ,{"이름":"사오정", "등급":"골드회원", "가입일":"20/02/13"} //2번방
               ];
   // console.log(list[0]["가입일"]);   
   let tag = '<table border ="1">';
       tag +="<tr><th>이름</th><th>등급</th><th>가입일</th></tr>";
       //반복적으로 작업 for문  <td>
        
       
        for(let i=0; i<list.length; ++i){
            let name      = list[i]["이름"]; //list의 i번째 방에서 꺼내와서 변수 만들어서 대입
            let grade     = list[i]["등급"];  
            let joindate  = list[i]["가입일"];
            tag +=`<tr><td>${name}</td><td>${grade}</td><td>${joindate}</td></tr>`;
            //백틱의 역할은 치환시켜준다(좀더 찾아보기)
        }
      
       tag+= '</table>';

       document.getElementById("target").innerHTML = tag;
            
}