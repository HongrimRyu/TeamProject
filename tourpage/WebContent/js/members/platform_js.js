/**
 * 플랫폼 자바스크립트파일
 */
	
/* 페이스북 */
window.fbAsyncInit = function() {
		FB.init({
			appId : '295567380987102',
			cookie : true,
			xfbml : true,
			version : 'v2.8'
		});

		FB.getLoginStatus(function(response) {
			console.log('statusChangeCallback');
			console.log(response);

			if (response.status === 'connected') {
				$("#result").append("status : connected");
				FB.logout();
			} else {
				$("#result").append(response);
			}
		});
	};

	(function(d, s, id) {
		var js,
			fjs = d.getElementsByTagName(s)[0];
		if (d.getElementById(id)) return;
		js = d.createElement(s);
		js.id = id;
		js.src = "https://connect.facebook.net/en_US/all.js#xfbml=1&appId=" + "295567380987102";
		fjs.parentNode.insertBefore(js, fjs);
	}(document, 'script', 'facebook-jssdk'));


	
	function fbLoginAction() {
		FB.login(function(response) {
			var fbname;
			var accessToken = response.authResponse.accessToken;
			FB.api('/me?fields=first_name,last_name,email,birthday,link,gender,locale,picture', function(response) {
				
				document.fr.email.value = response.email;
				document.fr.nickname.value = response.last_name + response.first_name;
				document.fr.platform.value = "fb";
				document.fr.submit();

			});
		}, {
			scope : 'public_profile, email'
		});
		
		FB.logout(function(response) {});
	}

	/* 구글 */
    function onSuccess(googleUser) {
		var profile = googleUser.getBasicProfile();
		console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
		console.log('Name: ' + profile.getName());
		console.log('Image URL: ' + profile.getImageUrl());
		console.log('Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.

		signOut();
		
		document.fr.email.value = profile.getEmail();
		document.fr.nickname.value = profile.getName();
		document.fr.platform.value = "go";
		document.fr.submit();
      }
      function onFailure(error) {
        console.log(error);
      }
      function renderButton() {
        gapi.signin2.render('my-signin2', {
          'scope': 'profile email',
          'width':300,
          'height': 40,
          'longtitle': true,
          'theme': 'light',
          'onsuccess': onSuccess,
          'onfailure': onFailure
        });
      }
	
	
//	function onSignIn(googleUser) {
//		var profile = googleUser.getBasicProfile();
//		console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
//		console.log('Name: ' + profile.getName());
//		console.log('Image URL: ' + profile.getImageUrl());
//		console.log('Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.
//
//		signOut();
//		
//		document.fr.email.value = profile.getEmail();
//		document.fr.nickname.value = profile.getName();
//		document.fr.platform.value = "go";
//		document.fr.submit();
//	}
//
	function signOut() {
		var auth2 = gapi.auth2.getAuthInstance();
		auth2.signOut().then(function() {
			console.log('User signed out.');
		});
	}
/* 네이버 */
	var naverLogin = new naver.LoginWithNaverId(
		{
			clientId : "52KKKxlWOjxrMKfzS0tN",
			callbackUrl : "http://localhost:8088/tourpage/Login.me",
			isPopup : false, /* 팝업을 통한 연동처리 여부 */
			loginButton : {
				color : "green",
				type :3,
				width : 300,
				height : 40,
				border:0
			} /* 로그인 버튼의 타입을 지정 */
		}
	);
	// 버튼을 클릭했을때만 이벤트
	/* 설정정보를 초기화하고 연동을 준비 */
	naverLogin.init();

	/* (4) Callback의 처리. 정상적으로 Callback 처리가 완료될 경우 main page로 redirect(또는 Popup close) */
	window.addEventListener('load', function() {
		naverLogin.getLoginStatus(function(status) {
			if (status) {
				/* (5) 필수적으로 받아야하는 프로필 정보가 있다면 callback처리 시점에 체크 */
				var email = naverLogin.user.getEmail();
				var nickname = naverLogin.user.getNickName();
				var name = naverLogin.user.getName();
				// var profileImage = naverLogin.user.getProfileImage();
				var birthday = naverLogin.user.getBirthday();
				var age = naverLogin.user.getAge();
				var gender = naverLogin.user.getGender();

				if (email == undefined || email == null) {
					alert("이메일은 필수정보입니다. 정보제공을 동의해주세요.");
					/* (5-1) 사용자 정보 재동의를 위하여 다시 네아로 동의페이지로 이동함 */
					naverLogin.reprompt();
					return;
				}

				naverLogin.logout();

				document.fr.name.value = name;
				document.fr.email.value = email;
				document.fr.nickname.value = nickname;
				document.fr.platform.value = "na";
				document.fr.submit();

			} else {
				console.log("callback 처리에 실패하였습니다.");
			}
		});
	});
	
	
	