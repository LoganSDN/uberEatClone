export interface RestaurantI {
	id?: number;
	name?: string;
	orders?: OrderI[];
	address?: AddressI;
	openingTime?: string;
	closingTime?: string;
	description?: string;
	menus?: MenuI[];
	image?: string;
}

export interface OrderI {
	id?: number;
	status?: string;
	totalAmount?: number;
	susId?: string;
	resId?: string;
	productsList?: ProductI[];
}

export interface AddressI {
	id?: number;
	street?: string;
	number?: number;
	ZIP?: string;
	city?: string;
}

export interface MenuI {
	id?: number;
	name?: string;
	productsList?: ProductI[];
}

export interface ProductI {
	id?: number;
	name?: string;
	description?: string;
	price?: number;
}