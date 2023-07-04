export interface UserI {
	id: number;
	firstName: string;
	lastName: string;
	email: string;
	password: string;
	ordersList: string;
	roles: RoleI[];
	username: string;
	authorities: AuthoritiesI[];
}

export interface RoleI {
	id: number;
	name: string;
}

export interface AuthoritiesI {
	authority: string;
}