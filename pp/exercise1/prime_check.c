#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <stdbool.h>

#define  MAX_SIZE 100

int* create_number()
{
	const int size = 2 * MAX_SIZE;
    const size_t bytes = size * sizeof(int);
    int* num = (int*)malloc(bytes);
    memset(num, 0, bytes);
	return num;
}

void reverse(char str[]) 
{
	int start = 0;
	int end = strlen(str) -1;
	while (start < end) 
	{
		char temp = str[start];
		str[start] = str[end];
		str[end] = temp;
		start++;
		end--;
	}
}

int* enter_number()
{
	char tmp[MAX_SIZE];
	scanf("%s", tmp);
	int len = strlen(tmp);
	reverse(tmp);
	int* num = create_number();
	for (int i=0; i<len; ++i)
		num[i] = (int)tmp[i] - '0';
	return num;
}

int* long_number(int num)
{
	int* res = create_number();
	for (int i=0; num; ++i) {
		res[i] = num%10;
		num /= 10;
	}
	return res;
}

int* copy(int* num)
{
	int* res = create_number();
	for (int i=0; i<MAX_SIZE; ++i)
		res[i] = num[i];
	return res;
}

int length(int* num)
{
	int len = 0;
	for(int i=0; i<2*MAX_SIZE; ++i)
		if (num[i] != 0)
			len = i+1;
	return len;
}

bool equal(int* num1, int* num2)
{
	for (int i=0; i<2*MAX_SIZE; ++i)
		if (num1[i] != num2[i])
			return false;
	return true;
}
	
bool compare(int* num1, int num2)
{
	int* long_num2 = long_number(num2);
	bool eq = equal(num1, long_num2);
	free(long_num2);
	return eq;
}

void print_number(int* num)
{
	int len = length(num);
	if (len == 0) {
		printf("0");
		return;
	}

	char tmp[MAX_SIZE];
	for (int i=0; i<len; ++i)
		tmp[i] = (char)(num[i] + '0');
	tmp[len] = 0;
	reverse(tmp);
	printf("%s", tmp);
}

int* multiply(int* num1, int* num2)
{
	int* res = create_number();
	for (int i=0; i<MAX_SIZE; ++i)
		for (int j=0; j<MAX_SIZE; ++j)
			res[i+j] += num1[i] * num2[j];
	
	int tmp = 0;
	for (int i=0; i<2*MAX_SIZE; ++i) {
		res[i] += tmp;
		tmp = res[i]/10;
		res[i] %= 10;
	}
	return res;
}

int* add(int* num1, int* num2)
{
	int* res = create_number();
	int tmp = 0;
	for (int i=0; i<MAX_SIZE; ++i) {
		res[i] = num1[i] + num2[i] + tmp;
		tmp = res[i]/10;
		res[i] %= 10;
	}
	res[MAX_SIZE] = tmp;
	return res;
}

bool larger_or_equal(int* num1, int* num2)
{
	for (int i=2*MAX_SIZE-1; i>-1; --i) {
		if (num1[i] > num2[i])
			return true;
		if (num1[i] < num2[i])
			return false;
	}
	return true;
}

int* subtract(int* num1, int* num2)
{
	int* res = create_number();
	if (!larger_or_equal(num1, num2))
		return res;
	int tmp = 0;
	for (int i=0; i<2*MAX_SIZE; ++i) {
		if (num1[i] >= num2[i]+tmp) {
			res[i] = num1[i] - num2[i]-tmp;
			tmp = 0;
		} else {
			res[i] = 10+num1[i] - num2[i]-tmp;
			tmp = 1;
		}
	}
	return res;
}

int* divide(int* num1, int* num2)
{
	int pow = 0;
	int* pow_ten = long_number(1);
	int* prod = multiply(num2, pow_ten);
	while (larger_or_equal(num1, prod)) {
		pow_ten[pow] = 0;
		++pow;
		pow_ten[pow] = 1;
		free(prod);
		prod = multiply(num2, pow_ten);
	}
	pow_ten[pow] = 0;
	--pow;
	int* res = create_number();
	int* cp = add(res, num1);
	while(pow >= 0) {
		pow_ten[pow] = 1;
		free(prod);
		prod = multiply(num2, pow_ten);
		while (larger_or_equal(cp, prod)) {
			++res[pow];
			int* tmp = subtract(cp, prod);
			free(cp);
			cp = tmp;
		}
		pow_ten[pow] = 0;
		--pow;
	}
	free(cp);
	free(pow_ten);
	free(prod);
	return res;
}

int* modulus(int* num, int* mod)
{
	int* q = divide(num, mod);
	int* prod = multiply(q, mod);
	int* res = subtract(num, prod);
	free(q);
	free(prod);
	return res;
}

int* fast_pow_mod(int* num, int* pow, int* mod)
{
	if (compare(pow, 0)) {
		int* res = create_number();
		res[0] = 1;
		return res;
	}
	int* square = multiply(num, num);
	int* square_mod = modulus(square, mod);
	free(square);
	int* two = long_number(2);
	int* half = divide(pow, two);
	free(two);
	int* res = fast_pow_mod(square_mod, half, mod);
	free(half);
	free(square_mod);
	if (pow[0]%2 == 1) {
		int* tmp = multiply(num, res);
		int* tmp2 = modulus(tmp, mod);
		free(res);
		free(tmp);
		res = tmp2;
	}
	return res;
}

int* random_number()
{
	int* res = create_number();
	for (int i=0; i<MAX_SIZE; ++i)
		res[i] = rand() % 10;
	return res;
}

bool fermat_check(int* num)
{
	int* rand_num = random_number();
	int* rand_base = modulus(rand_num, num);
	while (compare(rand_base, 0)) {
		free(rand_num);
		free(rand_base);
		rand_num = random_number();
		rand_base = modulus(rand_num, num);
	}
	free(rand_num);
	int* one = long_number(1);
	int* num_ = subtract(num, one);
	free(one);
	int* pow = fast_pow_mod(rand_base, num_, num);
	free(rand_base);
	free(num_);
	bool res = compare(pow, 1);
	free(pow);
	return res;
}

bool miller_check(int* num)
{
	int* rand_num = random_number();
	int* rand_base = modulus(rand_num, num);
	while (compare(rand_base, 0)) {
		free(rand_num);
		free(rand_base);
		rand_num = random_number();
		rand_base = modulus(rand_num, num);
	}
	free(rand_num);
	int* one = long_number(1);
	int* num_ = subtract(num, one);
	free(one);
	int* power = copy(num_);
	int* two = long_number(2);
	int* pow;
	do {
		pow = fast_pow_mod(rand_base, power, num);
		int* tmp = divide(power, two);
		free(power);
		power = tmp;
	} while (compare(pow, 1) && power[0]%2 == 0);
	bool res = (compare(pow, 1) || equal(pow, num_));
	free(power);
	free(pow);
	free(two);
	free(rand_base);
	free(num_);
	return res;
}

int main()
{
	int* num = enter_number();
	if (fermat_check(num))
		printf("fermat: num is prime\n");
	else
		printf("fermat: num is composite\n");
	if (miller_check(num))
		printf("miller: num is prime\n");
	else
		printf("miller: num is composite\n");
	bool multiple_miller = true;
	int number_of_iterations = 10;
	for (int i=0; i<number_of_iterations; ++i)
		multiple_miller = (multiple_miller && miller_check(num));
	if (multiple_miller)
		printf("%d millers: num is prime\n", number_of_iterations);
	else
		printf("%d millers: num is composite\n", number_of_iterations);

	free(num);

	return 0;    
}
