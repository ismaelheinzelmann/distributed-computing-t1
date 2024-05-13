FROM node:20-alpine AS development
ENV NODE_ENV development
COPY . .
RUN npm install
CMD ["npm", "start" ]
EXPOSE 3000