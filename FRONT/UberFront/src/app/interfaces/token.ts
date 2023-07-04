import { UserI } from "./user";

export interface TokenI {
	accessToken: string;
	email: string;
}

export interface DecodedTokenI {
	aud: string;
	exp: number;
	iat: number;
	iss: string;
	sub: string;
	user: UserI;
}
