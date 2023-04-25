use instaDB;

CREATE TABLE USERS (
                       USER_NO INT NOT NULL PRIMARY KEY AUTO_INCREMENT,		-- 회원번호
                       USER_ID VARCHAR(30) NOT NULL UNIQUE,					-- 회원아이디(닉네임)
                       USER_PW VARCHAR(255) NOT NULL,						-- 회원비밀번호
                       USER_NM	VARCHAR(30) NOT NULL,						-- 회원이름
                       USER_PHONE VARCHAR(20),								-- 회원핸드폰
                       USER_EMAIL VARCHAR(20),								-- 회원이메일
                       USER_INTRO VARCHAR(255),								-- 회원 소개
                       JOIN_DT DATETIME NOT NULL DEFAULT NOW(),				-- 가입일
                       DEL_DT DATETIME,										-- 탈퇴일
                       USER_ST VARCHAR(5)										-- 회원상태
);

DROP TABLE users;
SELECT * FROM users;

CREATE TABLE `BOARD`(
		BOARD_NO INT PRIMARY KEY AUTO_INCREMENT, 		--  글 번호
		USER_ID VARCHAR(30) NOT             , 			--  글쓴이
		BOARD_CONTENT TEXT NOT NULL,					--  글내용
		BOARD_PICTURE VARCHAR(255),               	    --  이미지
		WRITE_TIME DATETIME NOT NULL DEFAULT NOW(),	    --  작성시간
		UPDATE_TIME DATETIME,							--  수정시간
		BOARD_LIKES INT UNSIGNED  DEFAULT 0 			--  좋아요 수
);

SELECT * FROM BOARD;
DROP TABLE BOARD;

CREATE TABLE `BOARDCOMMENT`(
	COMMENT_NO INT PRIMARY KEY AUTO_INCREMENT,  -- 댓글 번호
	USER_ID VARCHAR(30) NOT NULL UNIQUE,        -- 글쓴이
	COMMENT_CONTENT VARCHAR(255) NOT NULL,      -- 댓글내용
	BOARD_NO INT UNSIGNED NOT NULL,   			-- 게시판 글 번호
	WRITE_TIME DATETIME NOT NULL DEFAULT NOW(), -- 댓글 작성 시간
	UPDATE_TIME DATETIME                        -- 댓글 수정 시간
);

SELECT * FROM BOARD;
DROP TABLE BOARD;