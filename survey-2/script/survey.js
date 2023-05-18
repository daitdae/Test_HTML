window.onload = function() {
    let result = "<h2>설문 결과</h2><ul>";

    document.getElementById("btn").onclick = function() {
        let name   = document.getElementById("name");         // 반드시 입력
        
        if(name.value.length < 1) {
            alert("이름을 입력하세요");
            name.focus();
            return;
        }
        result += "<li>이름 : " + name.value +"</li>";
        
        let phone1 = document.getElementById("phone1").value;
        let phone2 = document.getElementById("phone2");       // 숫자만
        if(isNaN(phone2.value) || phone2.value.length != 8) {
            alert("-를 제외한 정확한 길이의 숫자만 입력해주세요");
            phone2.value = "";
            phone2.focus();
            return;
        }

        let phone = phone1 + phone2.value;
        result += "<li>전화번호 : " + phone +"</li>";

        let email  = document.getElementById("email");        // @가 들어가도록
        if(!email.value.includes("@")) {
            alert("정확한 이메일 타입으로 입력해 주세요");
            email.value = "";
            email.focus();
            return;
        }
        result += "<li>Email : " + email.value +"</li>";

        let job    = document.getElementById("job").value;
		/*
        // 1) getElementsByClassName()를 이용하여 radio 버튼 가져오기
        let s      = document.getElementsByClassName("service");
 
        let service = "";
        for(let i =0; i<s.length; i++) {
            if(s[i].checked)  {
                service += s[i].value;
            }
        } */

        // 2) querySelector()를 이용하여 radio 버튼 가져오기
		let s = document.querySelector('input[type="radio"]:checked')
        let service = "";
        service += s.value;

        result += "<li>직업 : " + job +"</li>";
        result += "<li>가장 많이 사용하는 구글 서비스 : " + service +"</li>";

        /*
        // 1) getElementsByClassName()을 이용하여 checkbox 가져오기
        let t   = document.getElementsByClassName("tool");
        let tools = "";
        for(let i =0; i<t.length; i++) {
            if(t[i].checked)  {
                tools += t[i].value + " ";
            }
        }
        */

        // 2) querySelectorAll()을 이용하여 checkbox 가져오기
        let t = document.querySelectorAll('input[type="checkbox"]:checked');
        let tools = "";
        for(let i =0; i<t.length; i++) {
            tools += t[i].value + " ";
        }

        result += "<li>기능강화가 필요한 도구 : " + tools +"</li>";
        let etc    = document.getElementById("etc").value;
        result += "<li>남기실 말씀 : " + etc +"</li></ul>";

        document.getElementById("result").innerHTML = result;
    };
};