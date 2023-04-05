use instaDB;

CREATE TABLE USERS (
                       USER_NO INT NOT NULL PRIMARY KEY AUTO_INCREMENT,		-- 회원번호
                       USER_ID VARCHAR(30) NOT NULL UNIQUE,					-- 회원아이디(닉네임)
                       USER_PW VARCHAR(30) NOT NULL,							-- 회원비밀번호
                       USER_NM	VARCHAR(30) NOT NULL,							-- 회원이름
                       USER_PHONE VARCHAR(20),									-- 회원핸드폰
                       USER_EMAIL VARCHAR(20),									-- 회원이메일
                       JOIN_DT DATETIME NOT NULL DEFAULT NOW(),				-- 가입일
                       DEL_DT DATETIME,										-- 탈퇴일
                       USER_ST VARCHAR(5)										-- 회원상태
);