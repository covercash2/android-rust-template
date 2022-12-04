use jni::{JNIEnv, objects::JObject};

#[no_mangle]
pub unsafe extern "C" fn Java_dev_covercash_rust_addInts(
    _env: JNIEnv,
    _this: JObject,
    first: i32,
    second: i32,
) -> i32 {
    first + second
}
pub fn add(left: usize, right: usize) -> usize {
    left + right
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn it_works() {
        let result = add(2, 2);
        assert_eq!(result, 4);
    }
}
