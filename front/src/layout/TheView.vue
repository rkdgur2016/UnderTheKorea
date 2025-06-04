<template>
  <div class="relative flex size-full min-h-screen flex-col bg-slate-50 group/design-root overflow-x-hidden font-['Noto_Serif',_'Noto_Sans',_sans-serif]">
    <div class="layout-container flex h-full grow flex-col">
      <div class="px-40 flex flex-1 justify-center py-5">
        <div class="layout-content-container flex flex-col max-w-[960px] flex-1">
          <div class="px-4 py-3">
            <label class="flex flex-col min-w-40 h-12 w-full">
              <div class="flex w-full flex-1 items-stretch rounded-xl h-full">
                <div class="text-[#4e7297] flex border-none bg-[#e7edf3] items-center justify-center pl-4 rounded-l-xl border-r-0">
                  <svg xmlns="http://www.w3.org/2000/svg" width="24px" height="24px" fill="currentColor" viewBox="0 0 256 256">
                    <path d="M229.66,218.34l-50.07-50.06a88.11,88.11,0,1,0-11.31,11.31l50.06,50.07a8,8,0,0,0,11.32-11.32ZM40,112a72,72,0,1,1,72,72A72.08,72.08,0,0,1,40,112Z"></path>
                  </svg>
                </div>
                <input
                  placeholder="Search for topics..."
                  class="form-input flex w-full min-w-0 flex-1 resize-none overflow-hidden rounded-xl text-[#0e141b] focus:outline-0 focus:ring-0 border-none bg-[#e7edf3] focus:border-none h-full placeholder:text-[#4e7297] px-4 rounded-l-none border-l-0 pl-2 text-base font-normal leading-normal"
                  value=""
                />
              </div>
            </label>
          </div>
          <div class="@container">
            <div class="@[480px]:px-4 @[480px]:py-3">
              <div
                class="bg-cover bg-center flex flex-col justify-end overflow-hidden bg-slate-50 @[480px]:rounded-xl min-h-[218px]"
                style='background-image: linear-gradient(0deg, rgba(0, 0, 0, 0.4) 0%, rgba(0, 0, 0, 0) 25%), url("https://lh3.googleusercontent.com/aida-public/AB6AXuCbSGXK-k4eQPlBUWWS3t8tmFkgpGUVMi3D9OAyNJ02Fv4riBy7O3wF4O34cyLDiyCI7OJV4VPaoNyWmh2qFGnUhoOtY8tVrph2ZI3gvLYzi7nw9AdLBu3zxKv8MF-9nX6aK1ohvukmA_jxaqVlfk7UwN1a2nwNkv8zD6evO9INBYVNINKixD8kAh7JL8Ftj_N9cosaT-mEpWbJv5399LPn01_1YS3wHFIUg5ktIH8aMRPXBm5wrlffxmU752EpbvCCmsg12n69XUql");'
              >
                <div class="flex p-4"><p class="text-white tracking-light text-[28px] font-bold leading-tight">Welcome to Civic Discourse Hub</p></div>
              </div>
            </div>
          </div>
          <h2 class="text-[#0e141b] text-[22px] font-bold leading-tight tracking-[-0.015em] px-4 pb-3 pt-5">Table of Contents</h2>
          <div class="flex items-center gap-4 bg-slate-50 px-4 min-h-14 justify-between">
            <p class="text-[#0e141b] text-base font-normal leading-normal flex-1 truncate">Featured Topics</p>
            <div class="shrink-0">
              <div class="text-[#0e141b] flex size-7 items-center justify-center">
                <svg xmlns="http://www.w3.org/2000/svg" width="24px" height="24px" fill="currentColor" viewBox="0 0 256 256">
                  <path d="M181.66,133.66l-80,80a8,8,0,0,1-11.32-11.32L164.69,128,90.34,53.66a8,8,0,0,1,11.32-11.32l80,80A8,8,0,0,1,181.66,133.66Z"></path>
                </svg>
              </div>
            </div>
          </div>
          <div class="flex items-center gap-4 bg-slate-50 px-4 min-h-14 justify-between">
            <p class="text-[#0e141b] text-base font-normal leading-normal flex-1 truncate">Explore More</p>
            <div class="shrink-0">
              <div class="text-[#0e141b] flex size-7 items-center justify-center">
                <svg xmlns="http://www.w3.org/2000/svg" width="24px" height="24px" fill="currentColor" viewBox="0 0 256 256">
                  <path d="M181.66,133.66l-80,80a8,8,0,0,1-11.32-11.32L164.69,128,90.34,53.66a8,8,0,0,1,11.32-11.32l80,80A8,8,0,0,1,181.66,133.66Z"></path>
                </svg>
              </div>
            </div>
          </div>
          <h2 class="text-[#0e141b] text-[22px] font-bold leading-tight tracking-[-0.015em] px-4 pb-3 pt-5">Featured Topics</h2>
          <div class="p-4">
            <div class="flex items-stretch justify-between gap-4 rounded-xl">
              <div class="flex flex-col gap-1 flex-[2_2_0px]">
                <p class="text-[#0e141b] text-base font-bold leading-tight">Populism</p>
                <p class="text-[#4e7297] text-sm font-normal leading-normal">
                  Populism is a political approach that seeks to disrupt or dismantle existing institutions, often appealing to the common person against perceived elites.
                </p>
              </div>
              <div class="w-full bg-center bg-no-repeat aspect-video bg-cover rounded-xl flex-1" style='background-image: url("https://lh3.googleusercontent.com/aida-public/AB6AXuB6HBrrgj4m1x--SnFOr2_NOJiaIPE1hxGP090qMOzgeWKFuN06sZZyD8DrPuKz3c0QGg44BfZK-0fNKWkjKJm8bvyv3bqEtY_xP7i_DnAUvxoLKcOxhLZB2SIcL1IXBM4G3ysUsvp3qX6oU3eCJKnRzQJHQFJzaxRt7OM6ExK2PsV4ggtgnblxacP0HgmTq8W0Xi9TVfQUyQW9LZu17oR6BQ3cdFBNZZSE_mS7Gb6MrckT_Lz_eLsYborcpcSo6jbVNhldQUBMq3Oz");'></div>
            </div>
          </div>
          <div class="p-4">
            <div class="flex items-stretch justify-between gap-4 rounded-xl">
              <div class="flex flex-col gap-1 flex-[2_2_0px]">
                <p class="text-[#0e141b] text-base font-bold leading-tight">Opposition vs. Ruling Parties</p>
                <p class="text-[#4e7297] text-sm font-normal leading-normal">
                  Understanding the roles and responsibilities of opposition and ruling parties in a democratic system.
                </p>
              </div>
              <div class="w-full bg-center bg-no-repeat aspect-video bg-cover rounded-xl flex-1" style='background-image: url("https://lh3.googleusercontent.com/aida-public/AB6AXuAcH14ZRD1jdJoaZdq_GxTuBFF3dZipEl5y8378ABr01q5yuE4wUe3efzPUcql80I-7eIyAhzUxAaJ340P1FvkY1kZgyoMRPPL4OF3Wg7JSWXvrIHOa-XFFtS0wd2AgsLx3gvN4Vj_hBRdrFLF2kLXIFI-wp_JNzXVxXRopiJN9-u80FHDTnQAGYYp7tQ0ShDhBqCdZcquvHuBah1fJjDHZIWvuOyDDAFgYflu82Hpo1ESy1DkHU-snf8KRoU1WIj7nyC61rp0upo2C");'></div>
            </div>
          </div>
          <div class="p-4">
            <div class="flex items-stretch justify-between gap-4 rounded-xl">
              <div class="flex flex-col gap-1 flex-[2_2_0px]">
                <p class="text-[#0e141b] text-base font-bold leading-tight">Powers of Impeachment</p>
                <p class="text-[#4e7297] text-sm font-normal leading-normal">Explore the constitutional powers of impeachment and their implications in governance.</p>
              </div>
              <div class="w-full bg-center bg-no-repeat aspect-video bg-cover rounded-xl flex-1" style='background-image: url("https://lh3.googleusercontent.com/aida-public/AB6AXuDX826MeVsB9QK9Ix6exzDfBnhAvv-2KfkZMFsq3Cp_mOpP1mPMNxFJ_w116visuaDHiUON0eW3DcZ5KXWJGLfxRDt0UvTYjSBeZuYoNTZgzz7aVk4mmC5VJdpAs6oZ9TL1KV48GqwfpO4NekO5gLfa9ZhJXkwKfsVFXne_ejaxNE1dmQjU3wRgYyRJk1TJooDDOIJl9-7qWyxLlv1341i__Cc6DcQAhdxO4hX6767xOF3CPgKF2sOdkZJZbcxg6FD_XcqhnPPsipAa");'></div>
            </div>
          </div>
          <h2 class="text-[#0e141b] text-[22px] font-bold leading-tight tracking-[-0.015em] px-4 pb-3 pt-5">Explore More</h2>
          <div class="grid grid-cols-[repeat(auto-fit,minmax(158px,1fr))] gap-3 p-4">
            <div class="flex flex-1 gap-3 rounded-lg border border-[#d0dbe7] bg-slate-50 p-4 items-center">
              <div class="text-[#0e141b]">
                <svg xmlns="http://www.w3.org/2000/svg" width="24px" height="24px" fill="currentColor" viewBox="0 0 256 256">
                  <path d="M224,48H160a40,40,0,0,0-32,16A40,40,0,0,0,96,48H32A16,16,0,0,0,16,64V192a16,16,0,0,0,16,16H96a24,24,0,0,1,24,24,8,8,0,0,0,16,0,24,24,0,0,1,24-24h64a16,16,0,0,0,16-16V64A16,16,0,0,0,224,48ZM96,192H32V64H96a24,24,0,0,1,24,24V200A39.81,39.81,0,0,0,96,192Zm128,0H160a39.81,39.81,0,0,0-24,8V88a24,24,0,0,1,24-24h64Z"></path>
                </svg>
              </div>
              <h2 class="text-[#0e141b] text-base font-bold leading-tight">Political Theories</h2>
            </div>
            <div class="flex flex-1 gap-3 rounded-lg border border-[#d0dbe7] bg-slate-50 p-4 items-center">
              <div class="text-[#0e141b]">
                <svg xmlns="http://www.w3.org/2000/svg" width="24px" height="24px" fill="currentColor" viewBox="0 0 256 256">
                  <path d="M232,208a8,8,0,0,1-8,8H32a8,8,0,0,1-8-8V48a8,8,0,0,1,16,0v94.37L90.73,98a8,8,0,0,1,10.07-.38l58.81,44.11L218.73,90a8,8,0,1,1,10.54,12l-64,56a8,8,0,0,1-10.07-.38L96.39,114.29,40,163.63V200H224A8,8,0,0,1,232,208Z"></path>
                </svg>
              </div>
              <h2 class="text-[#0e141b] text-base font-bold leading-tight">Economic Systems</h2>
            </div>
            <div class="flex flex-1 gap-3 rounded-lg border border-[#d0dbe7] bg-slate-50 p-4 items-center">
              <div class="text-[#0e141b]">
                <svg xmlns="http://www.w3.org/2000/svg" width="24px" height="24px" fill="currentColor" viewBox="0 0 256 256">
                  <path d="M244.8,150.4a8,8,0,0,1-11.2-1.6A51.6,51.6,0,0,0,192,128a8,8,0,0,1-7.37-4.89,8,8,0,0,1,0-6.22A8,8,0,0,1,192,112a24,24,0,1,0-23.24-30,8,8,0,1,1-15.5-4A40,40,0,1,1,219,117.51a67.94,67.94,0,0,1,27.43,21.68A8,8,0,0,1,244.8,150.4ZM190.92,212a8,8,0,1,1-13.84,8,57,57,0,0,0-98.16,0,8,8,0,1,1-13.84-8,72.06,72.06,0,0,1,33.74-29.92,48,48,0,1,1,58.36,0A72.06,72.06,0,0,1,190.92,212ZM128,176a32,32,0,1,0-32-32A32,32,0,0,0,128,176ZM72,120a8,8,0,0,0-8-8A24,24,0,1,1,87.24,82a8,8,0,1,0,15.5-4A40,40,0,1,0,37,117.51,67.94,67.94,0,0,0,9.6,139.19a8,8,0,1,0,12.8,9.61A51.6,51.6,0,0,1,64,128,8,8,0,0,0,72,120Z"></path>
                </svg>
              </div>
              <h2 class="text-[#0e141b] text-base font-bold leading-tight">Philosophical Movements</h2>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'CivicDiscourseContent',
};
</script>

<style scoped>
/* You can add any component-specific styles here if needed,
   though Tailwind CSS handles most of the styling directly in the template. */
</style>