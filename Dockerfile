FROM ubuntu:22.04

# define environment variables for SDK
ENV ANDROID_SDK_ROOT=/opt/android-sdk
ENV PATH="${ANDROID_SDK_ROOT}/cmdline-tools/tools/bin:${ANDROID_SDK_ROOT}/platform-tools:${PATH}"
ENV DEBIAN_FRONTEND=noninteractive

# install dependencies
RUN dpkg --add-architecture i386 && \
    apt-get update && apt-get install -y \
    openjdk-18-jdk wget unzip tar curl git \
    libstdc++6:i386 zlib1g:i386 build-essential \
    libncurses6:i386 lib32gcc-s1 libglu1-mesa && \
    rm -rf /var/lib/apt/lists/*

# download and set up Android SDK tools
RUN mkdir -p "$ANDROID_SDK_ROOT/cmdline-tools" && \
    wget https://dl.google.com/android/repository/commandlinetools-linux-9477386_latest.zip -O /tmp/cmdline-tools.zip && \
    unzip /tmp/cmdline-tools.zip -d "$ANDROID_SDK_ROOT/cmdline-tools" && \
    mv "$ANDROID_SDK_ROOT/cmdline-tools/cmdline-tools" "$ANDROID_SDK_ROOT/cmdline-tools/tools" && \
    rm /tmp/cmdline-tools.zip

# accept SDK licenses and install build tools
RUN yes | sdkmanager --sdk_root=${ANDROID_SDK_ROOT} "platform-tools" \
    "build-tools;33.0.2" "platforms;android-33"

# working directory
WORKDIR /app

# copy code
COPY . .

